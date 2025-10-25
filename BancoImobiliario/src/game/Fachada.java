package game;

import java.util.ArrayList;
import java.util.List;


public class Fachada {

    private static Fachada instancia;
    private Banco banco;
    private Tabuleiro tabuleiro;
    private Dado dado;
    private List<Jogador> jogadores;
    private int indiceJogadorAtual;
    private Casa casaAtual;

    private Fachada() {
        this.banco = new Banco();
        this.tabuleiro = new Tabuleiro();
        this.dado = new Dado();
        this.jogadores = new ArrayList<>();
        this.indiceJogadorAtual = 0;
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
        return this.casaAtual.getTipo();
    }

    // === DADO ===
    public ArrayList<Integer> lancarDados() {
        return dado.lancarDados();
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
    
    public void processarTituloCasaAtual(int valorDados) {
        Jogador j = getJogadorAtual();
        Casa casa = tabuleiro.getCasa(j.getPosicao());

        if (casa instanceof CardTitulo prop) {
            if (prop.getDono() == null) {
            	// TRATAMENTO NO CONTROLLER
                System.out.println("Essa propriedade está disponível para compra por R$" + prop.getValor());
//                System.out.print("Deseja comprá-la? \n(1) sim \n(2) não\n");
//                int resposta = scanner.nextInt();
//                if (resposta == 1) {
//                    if (banco.venderTituloParaJogador(j, prop)) {
//                        prop.setDono(j);
//                        System.out.println("Você comprou " + prop.getNome());
//                    } else {
//                        System.out.println("Você não tem dinheiro suficiente.");
//                    }
//                }
            } else if (prop.getDono() != j) {
                this.pagarAluguel(valorDados);
            } else {
            	// TRATAMENTO NO CONTROLLER
//                if (prop instanceof CardPropriedade propriedade) {
//                    System.out.println("Deseja construir? 1=casa 2=hotel 3=não");
//                    int resp = scanner.nextInt();
//                    if (resp == 1) banco.construirCasaParaJogador(j, propriedade);
//                    if (resp == 2) banco.construirHotelParaJogador(j, propriedade);
//                }
            }
        }
    }
    
    
    public void pagarAluguel(int valorDados) {
        Jogador j = getJogadorAtual();
        CardTitulo casa = (CardTitulo) this.casaAtual;
        Jogador dono = casa.getDono();
    
        if (casa instanceof CardPropriedade propriedade) {
	        if (dono != null && dono != j && (propriedade.getCasas() > 0 || propriedade.isHotel())) {
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
    


    public void comprarCartaSorteReves() {
        // implementar depois
    }
}
