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
    private DeckSorteReves deckSorteReves; 

    // === OBSERVER: Lista de observadores ===
    // Assume que a interface JogoObserver está definida em game/JogoObserver.java
    private List<JogoObserver> observers; 

    private Fachada() {
        this.banco = new Banco();
        this.tabuleiro = new Tabuleiro();
        this.dado = new Dado();
        this.jogadores = new ArrayList<>();
        this.indiceJogadorAtual = 0;
        this.deckSorteReves = new DeckSorteReves();
        this.observers = new ArrayList<>(); // Inicializa a lista de observadores
    }

    // === SINGLETON ===
    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    // =======================================================
    // MÉTODOS OBSERVER (Observable)
    // =======================================================
    
    /**
     * NOVO: Adiciona um observador (Controller) à lista.
     * @param o O observador (deve implementar JogoObserver).
     */
    public void addObserver(JogoObserver o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    /**
     * NOVO: Remove um observador da lista.
     * @param o O observador a ser removido.
     */
    public void removeObserver(JogoObserver o) {
        observers.remove(o);
    }

    /**
     * NOVO: Notifica todos os observadores registrados sobre uma mudança de estado.
     * @param arg O objeto (DTO) que descreve a mudança (ex: Jogador, CartaSorteReves).
     */
    private void notifyObservers(Object arg) {
        for (JogoObserver observer : observers) {
            observer.update(this, arg); 
        }
    }
    
    // =======================================================
    // MÉTODOS DE JOGO (Com Notificações)
    // =======================================================

    // === JOGADORES ===
    public void adicionarJogador(int numero, String cor) {
        Jogador j = new Jogador(numero, cor);
        jogadores.add(j);
        // Notifica que um novo jogador foi adicionado
        notifyObservers(j); 
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
            // Notifica que o jogador atual mudou
            notifyObservers(getJogadorAtual()); 
        }
    }
    
    public String getCorJogadorAtual() {
        return getJogadorAtual().getCor();
    }

    // === TABULEIRO / MOVIMENTAÇÃO ===
    public TipoCasa moverJogador(int casas) {
    	Jogador j = jogadores.get(indiceJogadorAtual);
        this.casaAtual = tabuleiro.moverJogador(j, casas);
        
        // Notifica a nova posição e saldo (se passou pela Partida)
        notifyObservers(j); 
        
        return this.casaAtual.getTipo();
    }

    // === DADO ===
    public ArrayList<Integer> lancarDados() {
        ArrayList<Integer> valores = dado.lancarDados();
        // Notifica o resultado do lançamento dos dados
        notifyObservers(valores); 
        return valores;
    }

    // === BANCO ===
    public void impostoJogador() {
    	Jogador j = getJogadorAtual();
        banco.impostoJogador(j);
        // Notifica a mudança de saldo
        notifyObservers(j); 
    }

    public void premiacaoJogador() {
    	Jogador j = getJogadorAtual();
        banco.premiacaoJogador(j);
        // Notifica a mudança de saldo
        notifyObservers(j); 
    }

    public void transferirParaBanco(Jogador jogador, int valor) {
        banco.transferirParaBanco(jogador, valor);
        // Notifica a mudança de saldo
        notifyObservers(jogador); 
    }
    
    public void transferirDoBanco(Jogador jogador, int valor) {
        banco.transferirDoBanco(jogador, valor);
        // Notifica a mudança de saldo
        notifyObservers(jogador); 
    }

    public boolean venderTituloParaJogador(){
        Jogador j = getJogadorAtual();
        CardTitulo titulo = (CardTitulo) this.casaAtual;
    	boolean sucesso = banco.venderTituloParaJogador(j, titulo);
        if (sucesso) notifyObservers(j);
        return sucesso;
    }

    public boolean construirCasaParaJogador() {
    	Jogador j = getJogadorAtual();
    	CardPropriedade p = (CardPropriedade) this.casaAtual;
    	boolean sucesso = banco.construirCasaParaJogador(j, p);
        if (sucesso) {
            notifyObservers(j);
            notifyObservers(p); 
        }
    	return sucesso;
    }

    public boolean construirHotelParaJogador() {
    	Jogador j = getJogadorAtual();
    	CardPropriedade p = (CardPropriedade) this.casaAtual;
        boolean sucesso = banco.construirHotelParaJogador(j, p);
        if (sucesso) {
            notifyObservers(j);
            notifyObservers(p);
        }
        return sucesso;
    }

    public void pagarMultaPrisao(Jogador j) {
        banco.pagarMultaPrisao(j);
        notifyObservers(j); 
    }
    
    // === PRISÃO === 
    public void prenderJogador() {
        Jogador j = getJogadorAtual();
        j.setPreso(true);
        j.setPosicao(10); // Move para a prisão
        notifyObservers(j); 
    }
    
    public boolean jogadorIsPreso() {
    	Jogador j = getJogadorAtual();
    	return j.isPreso();
    }
    
    public void processarRodadaPrisao(){
        Jogador jogador = getJogadorAtual();
        if (jogador.isPreso()) {
        	ArrayList<Integer> valores = dado.lancarDados();
            notifyObservers(valores); // Notifica os dados lançados
            
        	System.out.println("Valores " + valores.get(0) + " e " + valores.get(1));
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
            notifyObservers(jogador); 
        }
    }

    public void soltarJogador() {
        getJogadorAtual().setPreso(false);
        notifyObservers(getJogadorAtual()); 
        System.out.println("Jogador " + getJogadorAtual().getCor() + " saiu da prisão!");
    }
    
    public boolean usarCartaSaidaLivreDaPrisao() {
        Jogador j = getJogadorAtual();
        if (j.usarCartaSaidaLivrePrisao()) { 
            deckSorteReves.devolverCartaSaidaLivre(); 
            notifyObservers(j); 
            notifyObservers(deckSorteReves); 
            return true;
        }
        return false;
    }
    
    
    public void verificarFalencia() {
        List<Jogador> jogadoresRemover = new ArrayList<>();
        
        Jogador j = getJogadorAtual();
        if (j.getDinheiro() < 0) {
            System.out.println("Jogador " + j.getCor() + " faliu e saiu do jogo!");
            jogadoresRemover.add(j);
        }
        
        for (Jogador jg : jogadores) {
             if (jg.getDinheiro() < 0 && !jogadoresRemover.contains(jg)) {
                System.out.println("Jogador " + jg.getCor() + " faliu e saiu do jogo!");
                jogadoresRemover.add(jg);
            }
        }
        
        jogadores.removeAll(jogadoresRemover);
        
        if (!jogadoresRemover.isEmpty()) {
            notifyObservers(jogadoresRemover); 
        }
    }
    
    // == TITULO ==
    
    public void processarTituloCasaAtual(int valorDados) {
        // ... (lógica de compra/construção/aluguel)
        // ... (chamar os métodos que já notificam)
    }
    
    
    public void pagarAluguel(int valorDados) {
        Jogador j = getJogadorAtual();
        CardTitulo casa = (CardTitulo) this.casaAtual;
        Jogador dono = casa.getDono();
        int aluguel = 0;
    
        if (casa instanceof CardPropriedade propriedade) {
	        if (dono != null && dono != j && (propriedade.getCasas() > 0 || propriedade.isHotel())) {
	            aluguel = propriedade.calcularAluguel(valorDados);
	            j.debito(aluguel);  
	            dono.credito(aluguel); 
	            System.out.println(j.getCor() + " pagou aluguel de " + aluguel + " a " + dono.getCor());
                notifyObservers(dono); 
	        }
	        
        } else if (casa instanceof CardCompanhia companhia) {
            if (dono != null && dono != j) {
                aluguel = companhia.calcularAluguel(valorDados);
                j.debito(aluguel);
                dono.credito(aluguel);
                System.out.println(j.getCor() + " pagou aluguel de R$" + aluguel + " para " + dono.getCor());
                notifyObservers(dono); 
            }
        }
        
        notifyObservers(j); // Notifica o jogador atual que pagou
        verificarFalencia(); // Verifica se o jogador atual faliu após pagar
    }
    

    // === SORTE OU REVÉS ===
    public CartaSorteReves processarCartaSorteReves() {
        Jogador j = getJogadorAtual();
        
        CartaSorteReves carta = deckSorteReves.tirarCarta();
        
        if (carta == null) {
            System.out.println("Erro: Deck de Sorte/Revés vazio!");
            return null;
        }
        
        // Notifica a carta ANTES de executar (para o Controller/View)
        notifyObservers(carta);

        System.out.println("O jogador " + j.getCor() + " comprou a carta: " + carta.getDescricao());
        
        switch (carta.getAcao()) {
            case RECEBER_DINHEIRO_BANCO:
                transferirDoBanco(j, carta.getValor());
                System.out.println(j.getCor() + " recebeu R$" + carta.getValor() + " do Banco. Saldo: R$" + j.getDinheiro());
                deckSorteReves.devolverCarta(carta);
                break;
                
            case PAGAR_DINHEIRO_BANCO:
                transferirParaBanco(j, carta.getValor());
                System.out.println(j.getCor() + " pagou R$" + carta.getValor() + " ao Banco. Saldo: R$" + j.getDinheiro());
                deckSorteReves.devolverCarta(carta);
                break;
                
            case RECEBER_DINHEIRO_JOGADORES:
                int valorPorJogador = carta.getValor();
                int valorTotalRecebido = 0;
                List<Jogador> jogadoresPagantes = new ArrayList<>();
                for (Jogador outro : new ArrayList<>(jogadores)) { 
                    if (outro != j) {
                        outro.debito(valorPorJogador);
                        j.credito(valorPorJogador);
                        valorTotalRecebido += valorPorJogador;
                        jogadoresPagantes.add(outro);
                    }
                }
                notifyObservers(j);
                for (Jogador pagante : jogadoresPagantes) {
                    notifyObservers(pagante);
                }
                System.out.println(j.getCor() + " recebeu um total de R$" + valorTotalRecebido + " dos outros jogadores. Saldo: R$" + j.getDinheiro());
                deckSorteReves.devolverCarta(carta);
                break;
                
            case SAIDA_LIVRE_PRISAO:
                j.adicionarCartaSaidaLivrePrisao();
                System.out.println(j.getCor() + " ganhou a carta SAÍDA LIVRE DA PRISÃO.");
                notifyObservers(j); 
                break;
                
            case MOVER_PARA_PONTO_PARTIDA:
                j.setPosicao(0); 
                j.credito(200); 
                System.out.println(j.getCor() + " avançou para o Ponto de Partida e recebeu R$200. Saldo: R$" + j.getDinheiro());
                deckSorteReves.devolverCarta(carta);
                notifyObservers(j); 
                break;
                
            case IR_PARA_PRISAO:
                prenderJogador(); 
                deckSorteReves.devolverCarta(carta);
                System.out.println(j.getCor() + " foi para a Prisão!");
                break;
                
            default:
                deckSorteReves.devolverCarta(carta);
                break;
        }

        verificarFalencia(); 
        
        return carta;
    }
    
    // Opcional: Getter para o Deck
    public DeckSorteReves getDeckSorteReves() {
        return deckSorteReves;
    }

    public void comprarCartaSorteReves() {
        processarCartaSorteReves();
    }
}