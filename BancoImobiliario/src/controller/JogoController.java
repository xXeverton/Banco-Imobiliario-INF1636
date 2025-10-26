package controller;

import game.Fachada;
import game.JogoObserver; // Importa a interface Observer
import game.CartaSorteReves; // Necessário para processar o objeto de notificação
import game.TipoAcaoCarta; // Necessário para verificar o tipo de ação da carta
import game.Jogador; // Necessário para checar tipos de notificação

import view.JogoView; // Referência à View

import java.util.ArrayList;
import javax.swing.JOptionPane; // Controller é responsável por mensagens

// O Controller implementa JogoObserver para receber notificações da Fachada
public class JogoController implements JogoObserver {
    private Fachada f;
    private JogoView view; // Referência à View para delegar a renderização

    /**
     * Construtor que inicializa a Fachada e registra o Controller como Observer.
     * @param view A JogoView principal do jogo.
     */
    public JogoController(JogoView view) {
        this.f = Fachada.getInstancia();  // Singleton
        this.view = view;
        this.f.addObserver(this); // O Controller se registra como Observer na Fachada
    }
    
    // =======================================================
    // MÉTODO DE OBSERVER (Recebe notificação do Model)
    // =======================================================
    @Override
    public void update(Fachada observable, Object arg) {
        
        // 1. Trata a notificação de uma Carta Sorte/Revés
        if (arg instanceof CartaSorteReves carta) {
            // Exibir a descrição no Controller (responsável por JOptionPane)
            JOptionPane.showMessageDialog(null,
                                          carta.getDescricao(),
                                          "Sorte ou Revés!",
                                          JOptionPane.INFORMATION_MESSAGE);
            
            // Repassa a carta para a View exibir a imagem (renderização)
            this.view.receberUpdate(carta); 

            // Se a carta for IR_PARA_PRISAO, o turno deve ser encerrado
            if (carta.getAcao() == TipoAcaoCarta.IR_PARA_PRISAO) {
                this.proximaRodada();
            }
        } 
        
        // 2. Trata a notificação de um Jogador (saldo, posição, estado de prisão, etc.)
        else if (arg instanceof Jogador jogador) {
             // Repassa o objeto Jogador para a View atualizar o placar e o pião
            this.view.receberUpdate(jogador);
        }
        
        // 3. Trata a notificação dos Dados (ArrayList<Integer>)
        else if (arg instanceof ArrayList<?> valoresDados) {
             // Repassa os valores para a View atualizar a imagem dos dados
             this.view.receberUpdate(valoresDados);
        }
        
        // Outros argumentos (Falência, Propriedade, etc.) seriam tratados aqui.
    }


    // =======================================================
    // MÉTODOS DE CONTROLE (Ações do Usuário)
    // =======================================================

    public void adicionarJogador(int numero, String cor) {
        f.adicionarJogador(numero, cor);
    }

    public int getJogadores() {
    	return f.getNumeroJogadores();
    }
    
    public String getCorJogadorAtual() {
    	return f.getCorJogadorAtual();
    }

    public ArrayList<Integer> lancarDados() {
    	ArrayList<Integer> valores = f.lancarDados();
    	
        System.out.println("Jogador " + f.getJogadorAtual().getCor() + " tirou " + valores.get(0) + "," + valores.get(1));
        return valores;
    }

    // Deslocar o jogador da vez
    public void moverJogador(int casas) {
        var tipo = f.moverJogador(casas); 

        switch (tipo) {
            case PARTIDA:
                System.out.println("Caiu na casa de Partida. Recebe R$200.");
                f.premiacaoJogador();
                break;

            case PRISAO:
                System.out.println("Está apenas visitando a prisão.");
                break;

            case VA_PARA_PRISAO:
                System.out.println("Você foi preso!");
                f.prenderJogador(); // Move para a prisão e notifica
                this.proximaRodada(); // Pula o turno
                break;

            case SORTE_REVES:
                System.out.println("Comprando carta Sorte ou Revés...");
                // A Fachada processa a lógica e notifica o resultado, que é tratado no update()
                f.processarCartaSorteReves(); 
                break;

            case IMPOSTO:
                System.out.println("Pagando imposto de R$200 ao banco.");
                f.impostoJogador();
                break;

            case LUCROS_DIVIDENDOS:
                System.out.println("Recebendo dividendo de R$200 do banco.");
                f.premiacaoJogador();
                break;

            case TITULO:
            	f.pagarAluguel(casas);
                break;

            default:
                System.out.println("Casa livre, nada acontece.");
                break;
        }
        
        f.verificarFalencia();
    }

    // =======================================================
    // LÓGICA DE PRISÃO (Ações do Jogador)
    // =======================================================
    
    // Novo método de controle para uso da carta (chamado pela View/Listener)
    public void tentarUsarCartaSaidaLivre() {
        if (f.getJogadorAtual().temCartaSaidaLivrePrisao()) {
             int resposta = JOptionPane.showConfirmDialog(null, 
                "Deseja usar sua carta 'Saída Livre da Prisão'?", 
                "Prisão", JOptionPane.YES_NO_OPTION);
            
            if (resposta == JOptionPane.YES_OPTION) {
                // A Fachada executa a lógica e notifica a View sobre a soltura e a perda da carta
                f.usarCartaSaidaLivreDaPrisao(); 
            }
        }
    }
    
    public boolean verificaPrisao() {
    	return f.jogadorIsPreso();
    }
    
    public void processarPrisao() {
        f.processarRodadaPrisao();
    }

    public void verificarFalencia() {
        f.verificarFalencia();
    }

    public void proximaRodada() {
        f.proximoJogador();
    }
    
}