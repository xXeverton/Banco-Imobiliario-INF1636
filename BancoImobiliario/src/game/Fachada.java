package game;

import game.observer.Observavel;
import game.persistencia.EstadoJogador;
import game.persistencia.EstadoJogo;
import game.persistencia.SaveManager;
import eventos.*;

import java.io.File;
import java.io.IOException;
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
    
    // === MÉTODOS DE CONTROLE DO JOGO ===
    
    public void resetarJogo() {
        this.jogadores.clear();
        this.indiceJogadorAtual = 0;

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
        if (indice >= 0 && indice < jogadores.size()) {
            return jogadores.get(indice);
        }
        return null;
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
        Jogador j = getJogadorAtual();
        return (j != null) ? j.getCor() : "";
    }
    
    public ArrayList<String> getCorJogadores() {
        ArrayList<String> cores = new ArrayList<>();
        for (Jogador j : jogadores) {
            cores.add(j.getCor());
        }
        return cores;
    }
    
    public void notificarInfos(int indice) {
        Jogador j = getJogador(indice);
        if (j == null) return;

        double dinheiro = j.getDinheiro();
        ArrayList<String> propriedades_j = new ArrayList<>();
        for (CardPropriedade prop : j.getPropriedades()) {
            propriedades_j.add(prop.getNome());
        }

        ArrayList<Integer> companhias_j = new ArrayList<>();
        for (CardCompanhia comp : j.getCompanhias()) {
            companhias_j.add(comp.getIdImage());
        }

        EventoJogo evento = EventoInfosJogador.criar(
                dinheiro,
                propriedades_j,
                companhias_j,
                j.temHabeasCorpus(),
                j.getCor()
        );
        notificarObservadores(evento);
    }


    // === TABULEIRO / MOVIMENTAÇÃO ===
    public TipoCasa moverJogador(int casas) {
        if (jogadores.isEmpty()) return null;
        
    	Jogador j = jogadores.get(indiceJogadorAtual);
        this.casaAtual = tabuleiro.moverJogador(j, casas);
        
        EventoMoverJogador evento = new EventoMoverJogador(indiceJogadorAtual, casas);
        notificarObservadores(evento);
        return this.casaAtual.getTipo();
    }
    
    public void notificarCartaCasa() {
    	Casa casa = this.casaAtual;
    	Jogador j = getJogadorAtual();
    	if (j == null) return;
    	
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
        	if (titulo.getIdImage() == -1) {
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
        if (j == null) return;
        
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
                System.out.println("Ação da carta não implementada.");
        }
        this.deckSorteReves.devolverCarta(carta);
    }
    
	// === DADO ===
    public ArrayList<Integer> lancarDados() {
    	ArrayList<Integer> valores = dado.lancarDados();
        int dado1 = valores.get(0);
        int dado2 = valores.get(1);
        notificarObservadores(new EventoLancarDados(dado1, dado2));
        return valores;
    }
    
    public void setModoTeste(boolean modo) { dado.setModoTeste(modo); }
    public void setValoresTeste(int d1, int d2) { dado.setValoresTeste(d1, d2); }
    public void setModoTesteCartas(boolean modo) { deckSorteReves.setModoTeste(modo); }
    public void setProximaCartaTeste(int idCarta) { deckSorteReves.setProximaCartaTeste(idCarta); }
    
    private void moverJogadorParaPartida() {
        Jogador j = getJogadorAtual();
        if (j == null) return;
        j.setPosicao(0);
        this.premiacaoJogador(); 
        notificarObservadores(new EventoMoverJogadorParaPartida(indiceJogadorAtual));
    }

    // === BANCO E TRANSAÇÕES ===
    public void impostoJogador() {
    	Jogador j = getJogadorAtual();
        if (j != null) banco.impostoJogador(j);
    }

    public void premiacaoJogador() {
    	Jogador j = getJogadorAtual();
        if (j != null) banco.premiacaoJogador(j);
    }
    
    public boolean construirCasaParaJogador() {
    	Jogador j = getJogadorAtual();
    	if (j == null || !(this.casaAtual instanceof CardPropriedade)) return false;
    	return banco.construirCasaParaJogador(j, (CardPropriedade) this.casaAtual);
    }

    public boolean construirHotelParaJogador() {
    	Jogador j = getJogadorAtual();
    	if (j == null || !(this.casaAtual instanceof CardPropriedade)) return false;
        return banco.construirHotelParaJogador(j, (CardPropriedade) this.casaAtual);
    }

    public void pagarMultaPrisao(Jogador j) {
        banco.pagarMultaPrisao(j);
    }
    
    // === PRISÃO === 
    public void prenderJogador() {
        Jogador j = getJogadorAtual();
        if (j != null) {
            j.setPreso(true);
            j.setPosicao(10);
            EventoMoverJogadorPrisao evento = new EventoMoverJogadorPrisao(indiceJogadorAtual);
            notificarObservadores(evento);
        }
    }
    
    // Proteção contra NullPointer
    public boolean jogadorIsPreso() {
    	Jogador j = getJogadorAtual();
    	if (j == null) return false; 
    	return j.isPreso();
    }
    
    public void processarRodadaPrisao(){
        Jogador jogador = getJogadorAtual();
        if (jogador == null) return;
        
        if (jogador.isPreso()) {
            if (jogador.temHabeasCorpus()) {
                jogador.usarHabeasCorpus(); 
                this.soltarJogador();
                return; 
            }
            
        	ArrayList<Integer> valores = dado.lancarDados();
        	if (valores.get(0) == valores.get(1)) {
        		jogador.setRodadasPreso(0);
                this.soltarJogador();
                return;
        	}
            jogador.incrementarRodadaPreso();
            
            if (jogador.getRodadasPreso() > 3) {
            	banco.pagarMultaPrisao(jogador);
                jogador.setRodadasPreso(0);
                this.soltarJogador();
            }
        }
    }

    public void soltarJogador() {
        Jogador j = getJogadorAtual();
        if (j != null) j.setPreso(false);
    }
    
    public void verificarFalencia() {
        Jogador j = getJogadorAtual();
        if (j != null && j.getDinheiro() < 0) {
            jogadores.remove(j);
            if (indiceJogadorAtual >= jogadores.size()) indiceJogadorAtual = 0;
            
        }
    }
    
    // == TÍTULOS E PROPRIEDADES ==
    
    public String getNomeCasaAtual(){
    	return (this.casaAtual != null) ? this.casaAtual.getNome() : "";
    }
    
    public boolean processarTituloCasaAtual() {
        Casa casa = this.casaAtual;
        Jogador j = getJogadorAtual();
        if (j != null && casa instanceof CardTitulo titulo) {
        	return banco.venderTituloParaJogador(j, titulo);
        }
        return false;
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
        if (j == null || !(this.casaAtual instanceof CardTitulo)) return;
        
        CardTitulo casa = (CardTitulo) this.casaAtual;
        Jogador dono = casa.getDono();
    
        if (dono != null && dono != j) {
            int aluguel = 0;
            if (casa instanceof CardPropriedade propriedade) {
                aluguel = propriedade.calcularAluguel(valorDados);
            } else if (casa instanceof CardCompanhia companhia) {
                aluguel = companhia.calcularAluguel(valorDados);
            }
            j.debito(aluguel);  
            dono.credito(aluguel); 
        }
    }
    
    public double getDinheiroJogadorAtual() {
        Jogador j = getJogadorAtual();
        return (j != null) ? j.getDinheiro() : 0;
    }
    
    // === SALVAMENTO/CARREGAMENTO ===
    
    public boolean salvarJogo(File arquivo) throws Exception {
        try {
            EstadoJogo estado = this.gerarEstado();
            SaveManager.salvar(estado, arquivo); 
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean carregarJogo(File arquivo) throws Exception {
        try {
            EstadoJogo estado = SaveManager.carregar(arquivo);
            this.restaurarEstado(estado);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Salvar estado
    public EstadoJogo gerarEstado() {
        EstadoJogo estado = new EstadoJogo();
        estado.indiceJogadorAtual = indiceJogadorAtual;

        for (Jogador j : jogadores) {
            EstadoJogador ej = new EstadoJogador();
            ej.numero = j.getNumero_jogador();
            ej.cor = j.getCor();
            ej.dinheiro = j.getDinheiro();
            ej.posicao = j.getPosicao();
            ej.preso = j.isPreso();
            ej.habeas = j.temHabeasCorpus();
            ej.propriedades = new ArrayList<>(j.getIdPropriedades());
            ej.companhias = new ArrayList<>(j.getIdCompanhias());
            estado.jogadores.add(ej);
        }
        return estado;
    }
    
    // Carregar estado
    public void restaurarEstado(EstadoJogo estado) {
        jogadores.clear();

        for (EstadoJogador ej : estado.jogadores) {
            Jogador j = new Jogador(ej.numero, ej.cor);
            j.setDinheiro(ej.dinheiro);
            j.setPosicao(ej.posicao);
            j.setPreso(ej.preso);

            if (ej.habeas) j.addHabeasCorpus();

            for (String id : ej.propriedades) {
                CardPropriedade p = tabuleiro.getPropriedadePorId(id);
                if (p != null) {
                    j.adicionarPropriedade(p);
                    p.setDono(j);
                }
            }
            for (int id : ej.companhias) {
                CardCompanhia c = tabuleiro.getCompanhiaPorId(id);
                if (c != null) {
                    j.adicionarCompanhia(c);
                    c.setDono(j);
                }
            }
            jogadores.add(j);
        }
        indiceJogadorAtual = estado.indiceJogadorAtual;
    }
    
    // === RELATÓRIO FINAL ===
    public String gerarRelatorioFinal() {
        if (jogadores.isEmpty()) return "Não há jogadores.";

        jogadores.sort((j1, j2) -> Double.compare(j2.getDinheiro(), j1.getDinheiro()));

        StringBuilder sb = new StringBuilder();
        sb.append("🏆 FIM DE JOGO! 🏆\n\n");
        
        Jogador vencedor = jogadores.get(0);
        sb.append("VENCEDOR: ").append(vencedor.getCor())
          .append(" com R$ ").append(String.format("%.2f", vencedor.getDinheiro()))
          .append("\n\nClassificação Final:\n");

        int pos = 1;
        for (Jogador j : jogadores) {
            sb.append(pos).append("º - ").append(j.getCor())
              .append(" (R$ ").append(String.format("%.2f", j.getDinheiro())).append(")\n");
            pos++;
        }
        return sb.toString();
    }
}