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
        notificarObservadores(evento); // Método do Observável
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
    
    public void processarRodadaPrisao(){
        Jogador jogador = getJogadorAtual();
        if (jogador.isPreso()) {
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
    
}
