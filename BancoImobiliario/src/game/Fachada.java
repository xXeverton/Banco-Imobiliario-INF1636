package game;
import game.observer.Observavel;
import eventos.*;
import java.util.ArrayList;
import java.util.List;


public class Fachada extends Observavel {

    private static Fachada instancia;
    private Banco banco;
    private Tabuleiro tabuleiro;
    private Dado dado;
    private List<Jogador> jogadores;
    private int indiceJogadorAtual;
    private Casa casaAtual;
    private DeckSorteReves deckSorteReves;
    
    private Fachada() {
        this.banco = new Banco();
        this.tabuleiro = new Tabuleiro();
        this.dado = new Dado();
        this.jogadores = new ArrayList<>();
        this.indiceJogadorAtual = 0;
        this.deckSorteReves = new DeckSorteReves();
    }

    // === SINGLETON ===
    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    // === JOGADORES ===
    public void adicionarJogador(int numero, String cor) {
        Jogador j = new Jogador(numero, cor);
        jogadores.add(j);
    }

    public int getNumeroJogadores() {
        return jogadores.size();
    }

    public Jogador getJogador(int indice) {
        return jogadores.get(indice);
    }

    public Jogador getJogadorAtual() {
        if (jogadores.isEmpty()) return null;
        return jogadores.get(indiceJogadorAtual);
    }

    public void proximoJogador() {
        if (!jogadores.isEmpty()) {
            indiceJogadorAtual = (indiceJogadorAtual + 1) % jogadores.size();
        }
    }
    
    public String getCorJogadorAtual() {
        return getJogadorAtual().getCor();
    }
    
    public void notificarInfos(int indice) {
        Jogador j = jogadores.get(indice);

        double dinheiro = j.getDinheiro();
        ArrayList<CardPropriedade> propriedades = j.getPropriedades();
        ArrayList<CardCompanhia> companhias = j.getCompanhias();
        boolean temHabeas = j.temHabeasCorpus();
        String cor = j.getCor();

        ArrayList<String> propriedades_j = new ArrayList<>();
        for (CardPropriedade prop : propriedades) {
            propriedades_j.add(prop.getNome());
        }

        ArrayList<Integer> companhias_j = new ArrayList<>();
        for (CardCompanhia comp : companhias) {
            companhias_j.add(comp.getIdImage());
        }

        // 🔹 Criar evento com todas as infos
        EventoJogo evento = EventoInfosJogador.criar(
                dinheiro,
                propriedades_j,
                companhias_j,
                temHabeas,
                cor
        );

        // 🔹 Notificar observadores (View)
        notificarObservadores(evento);
    }


    // === TABULEIRO / MOVIMENTAÇÃO ===
    public TipoCasa moverJogador(int casas) {
    	Jogador j = jogadores.get(indiceJogadorAtual);
        this.casaAtual = tabuleiro.moverJogador(j, casas);
        
        EventoMoverJogador evento = new EventoMoverJogador(indiceJogadorAtual, casas);
        notificarObservadores(evento);
        return this.casaAtual.getTipo();
    }
    
    public void notificarCartaCasa() {
    	Casa casa = this.casaAtual;
    	Jogador j = getJogadorAtual();
    	boolean compra;
    	boolean dono = false;
    	if (casa instanceof CardTitulo titulo) {
    		if (titulo.getDono() == null) {
    			compra = true;
    		} else {
    			compra = false;
    			if (titulo.getDono() == j) {
    				dono = true;
    			}
    		}
        	if (titulo.getIdImage()== -1) {
        		EventoExibirCarta evento = new EventoExibirCarta(2, titulo.getIdImage(), titulo.getNome(), compra, dono);
        		notificarObservadores(evento);
        	} else {
        		EventoExibirCarta evento = new EventoExibirCarta(3, titulo.getIdImage(), titulo.getNome(), compra, dono);
        		notificarObservadores(evento);
        	}
        }
    }
    

    public void notificarCartaSorteReves() {
        CartaSorteReves carta = this.deckSorteReves.tirarCarta();
        
        EventoExibirCartaSorteReves evento = new EventoExibirCartaSorteReves(carta);
        notificarObservadores(evento); 
        
        TipoAcaoCarta acao = carta.getAcao();
        Jogador j = getJogadorAtual();
        
        switch (acao) {
            case IR_PARA_PRISAO:
                this.prenderJogador(); 
                break;
            
            case MOVER_PARA_PONTO_PARTIDA:
                this.moverJogadorParaPartida();
                break;
            
            case PAGAR_DINHEIRO_BANCO:
                j.debito(carta.getValor());
                break;
                
            case RECEBER_DINHEIRO_BANCO:
                j.credito(carta.getValor());
                break;
                
            case SAIDA_LIVRE_PRISAO:
                j.addHabeasCorpus();
                break;

            case RECEBER_DINHEIRO_JOGADORES:
                for (Jogador jogador : jogadores) {
                    if (jogador != j) {
                        jogador.debito(carta.getValor());
                        j.credito(carta.getValor());
                    }
                }
                break;

            default:
                System.out.println("Ação da carta '" + acao + "' (ID: " + carta.getId() + ") ainda não implementada.");
        }
        
        this.deckSorteReves.devolverCarta(carta);
    }
    
	// === DADO ===
    public ArrayList<Integer> lancarDados() {
    	ArrayList<Integer> valores = dado.lancarDados(); // usa a sua classe Dado.java
        int dado1 = valores.get(0);
        int dado2 = valores.get(1);

        notificarObservadores(new EventoLancarDados(dado1, dado2));
        return valores;
    }
    
    // Novos métodos para o modo teste
    public void setModoTeste(boolean modo) {
    	dado.setModoTeste(modo);
    }
    
    public void setValoresTeste(int d1, int d2) {
    	dado.setValoresTeste(d1, d2);
    }
    
    // == Deck Sorte ou Reves
    public void setModoTesteCartas(boolean modo) {
        deckSorteReves.setModoTeste(modo);
    }
    
    public void setProximaCartaTeste(int idCarta) {
        deckSorteReves.setProximaCartaTeste(idCarta);
    }
    
    private void moverJogadorParaPartida() {
        Jogador j = getJogadorAtual();
        j.setPosicao(0);
        
        this.premiacaoJogador(); 

        notificarObservadores(new EventoMoverJogadorParaPartida(indiceJogadorAtual));
    }

    // === BANCO ===
    public void impostoJogador() {
    	Jogador j = getJogadorAtual();
        banco.impostoJogador(j);
    }

    public void premiacaoJogador() {
    	Jogador j = getJogadorAtual();
        banco.premiacaoJogador(j);
    }

    public boolean venderTituloParaJogador(){
        Jogador j = getJogadorAtual();
        CardTitulo titulo = (CardTitulo) this.casaAtual;
    	return banco.venderTituloParaJogador(j, titulo);
    }

    public boolean construirCasaParaJogador() {
    	Jogador j = getJogadorAtual();
    	CardPropriedade p = (CardPropriedade) this.casaAtual;
    	return banco.construirCasaParaJogador(j, p);
    }

    public boolean construirHotelParaJogador() {
    	Jogador j = getJogadorAtual();
    	CardPropriedade p = (CardPropriedade) this.casaAtual;
        return banco.construirHotelParaJogador(j, p);
    }

    public void pagarMultaPrisao(Jogador j) {
        banco.pagarMultaPrisao(j);
    }
    
    // === PRISÃO === 
    public void prenderJogador() {
        Jogador j = getJogadorAtual();
        j.setPreso(true);
        j.setPosicao(10);
        EventoMoverJogadorPrisao evento = new EventoMoverJogadorPrisao(indiceJogadorAtual);
        notificarObservadores(evento);
    }
    
    
    public boolean jogadorIsPreso() {
    	Jogador j = getJogadorAtual();
    	return j.isPreso();
    }
    
	/*
	 * public void processarRodadaPrisao(){ Jogador jogador = getJogadorAtual(); if
	 * (jogador.isPreso()) { ArrayList<Integer> valores = dado.lancarDados();
	 * System.out.println("Valores " + valores.get(0) + " e " + valores.get(1)); if
	 * (valores.get(0) == valores.get(1)) { jogador.setRodadasPreso(0);
	 * this.soltarJogador(); return; } jogador.incrementarRodadaPreso();
	 * System.out.println("Jogador " + jogador.getCor() + " está preso há " +
	 * jogador.getRodadasPreso() + " rodada(s).");
	 * 
	 * if (jogador.getRodadasPreso() == 3) {
	 * System.out.println("Próxima rodada o jogador " + jogador.getCor() +
	 * " saira do banco pagando uma multa de R$50 caso não acerte os dados"); }
	 * 
	 * if (jogador.getRodadasPreso() > 3) { banco.pagarMultaPrisao(jogador);
	 * jogador.setRodadasPreso(0); this.soltarJogador(); }
	 * 
	 * } }
	 */
    
    public void processarRodadaPrisao(){
        Jogador jogador = getJogadorAtual();
        
        if (jogador.isPreso()) {
            
            if (jogador.temHabeasCorpus()) {
                jogador.usarHabeasCorpus(); // Gasta a carta
                this.soltarJogador();
                System.out.println("Jogador " + jogador.getCor() + " usou 'Saída Livre da Prisão'!");
                return; // Sai da prisão, não joga os dados
            }
            
        	ArrayList<Integer> valores = dado.lancarDados();
        	System.out.println("Valores " + valores.get(0) + " e " + valores.get(1));
        	if (valores.get(0) == valores.get(1)) {
        		jogador.setRodadasPreso(0);
                this.soltarJogador();
                return;
        	}
            jogador.incrementarRodadaPreso();
            System.out.println("Jogador " + jogador.getCor() + 
                               " está preso há " + jogador.getRodadasPreso() + " rodada(s).");
            
            if (jogador.getRodadasPreso() == 3) {
            	System.out.println("Próxima rodada o jogador " + jogador.getCor() + " saira do banco pagando uma multa de R$50 caso não acerte os dados");
            }
            
            if (jogador.getRodadasPreso() > 3) {
            	banco.pagarMultaPrisao(jogador);
                jogador.setRodadasPreso(0);
                this.soltarJogador();
            }
            
        }
    }

    public void soltarJogador() {
        getJogadorAtual().setPreso(false);
        System.out.println("Jogador " + getJogadorAtual().getCor() + " saiu da prisão!");
    }
    
    
    public void verificarFalencia() {
        Jogador j = getJogadorAtual();
        if (j.getDinheiro() < 0) {
            System.out.println("Jogador " + j.getCor() + " faliu e saiu do jogo!");
            jogadores.remove(j);
        }
    }
    
    // == TITULO ==
    
    public String getNomeCasaAtual(){
    	return this.casaAtual.getNome();
    }
    
    public boolean processarTituloCasaAtual() {
        Casa casa = this.casaAtual;
        Jogador j = getJogadorAtual();
        if (casa instanceof CardTitulo prop) {
        	if (j.getDinheiro()>prop.getValor()) {
        		prop.setDono(j);
        		j.debito(prop.getValor());
        		return true;
        	} else {
        		return false;
        	}
        } else {
        	return false;
        }
    }
    
    public boolean verificaConstrucaoHotel() {
    	Casa casa = this.casaAtual;
    	if (casa instanceof CardPropriedade prop) {
    		if (prop.getCasas() > 0) {
    			return prop.isHotel();
    		}
    	}
    	return true;
    }
    
    
    
    public void pagarAluguel(int valorDados) {
        Jogador j = getJogadorAtual();
        CardTitulo casa = (CardTitulo) this.casaAtual;
        Jogador dono = casa.getDono();
    
        if (casa instanceof CardPropriedade propriedade) {
	        if (dono != null && dono != j) {
	            int aluguel = propriedade.calcularAluguel(valorDados);
	            j.debito(aluguel);  
	            dono.credito(aluguel); 
	            System.out.println(j.getCor() + " pagou aluguel de " + aluguel + " a " + dono.getCor());
	        }
	        
        } else if (casa instanceof CardCompanhia companhia) {
            if (dono != null && dono != j) {
                int aluguel = companhia.calcularAluguel(valorDados);
                j.debito(aluguel);
                dono.credito(aluguel);
                System.out.println(j.getCor() + " pagou aluguel de R$" + aluguel + " para " + dono.getCor());
            }
        }
    }
    
    // Só pra visualizar o dinheiro no console
    public double getDinheiroJogadorAtual() {
        Jogador j = getJogadorAtual();
        if (j != null) {
            return j.getDinheiro();
        }
        return 0; 
    }
}
