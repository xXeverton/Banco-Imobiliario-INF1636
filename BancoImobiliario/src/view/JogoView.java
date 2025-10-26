package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.JogoController;
import game.Jogador; // Importação necessária
import game.CartaSorteReves; // Importação necessária

public class JogoView extends JFrame {

    private static final long serialVersionUID = 1L;
    // O Controller é inicializado no construtor, passando 'this'
    private final JogoController controller; 
    
    private final TabuleiroView tabuleiroView = new TabuleiroView();
    private final JButton btnLancarDados = new JButton("🎲 Lançar Dados");
    private final JButton btnProximoJogador = new JButton("➡ Próximo Jogador");
    private final JLabel lblStatus = new JLabel("Bem-vindo ao Banco Imobiliário!");
    
    // Componentes para Placar Lateral
    private final Map<String, JLabel> placaresJogadores = new HashMap<>(); 
    private final JPanel painelJogadores = new JPanel();

    private String[] cores = {"vermelho", "azul", "laranja", "amarelo", "roxo", "cinza"};

    private int numJogadores;
    // Este campo indica o índice 0-based do jogador atual para fins de status de turno.
    private int jogadorAtual = 0; 
    
    // 🔹 Construtor
    public JogoView() {
        super("Banco Imobiliário");
        
        // CORREÇÃO CRÍTICA: Instancia o Controller passando 'this'
        this.controller = new JogoController(this);

        // Painel Lateral de Jogadores
        painelJogadores.setLayout(new BoxLayout(painelJogadores, BoxLayout.Y_AXIS));
        painelJogadores.setBorder(BorderFactory.createTitledBorder("Placar"));
        
        // Configuração da Janela
        setLayout(new BorderLayout());
        add(tabuleiroView, BorderLayout.CENTER);
        add(painelJogadores, BorderLayout.WEST); 

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnLancarDados);
        painelBotoes.add(btnProximoJogador);
        painelBotoes.add(lblStatus);
        add(painelBotoes, BorderLayout.SOUTH);

        setSize(1280, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configurarListeners();

        this.iniciarJogo();
        
        setVisible(true);
    }
    
    // =======================================================
    // MÉTODOS PÚBLICOS DE ATUALIZAÇÃO (FIM DO PADRÃO OBSERVER)
    // =======================================================
    
    /**
     * Ponto de entrada para atualizações do Model, chamado pelo JogoController (Observer).
     * @param arg O objeto com a informação da mudança (Jogador, CartaSorteReves, ArrayList dos dados).
     */
    public void receberUpdate(Object arg) {
        if (arg instanceof Jogador jogador) {
            // Atualiza posição do pião e saldo do jogador
            // O índice do pino é 0-based. O número do jogador é 1-based.
            atualizarPino(jogador.getNumero_jogador() - 1, jogador.getPosicao()); 
            atualizarPlacar(jogador.getCor(), jogador.getDinheiro());
            
            // Atualiza o indicador de cor de quem joga
            tabuleiroView.setCorJogadorAtual(corPara(controller.getCorJogadorAtual()));

            // Apenas para feedback de status
            if (jogador.getNumero_jogador() == (jogadorAtual + 1)) {
                lblStatus.setText("Vez de " + jogador.getCor() + ". Saldo: $" + (int)jogador.getDinheiro());
            }

        } else if (arg instanceof CartaSorteReves carta) {
            // Exibe a imagem da carta
            exibirCarta(carta.getIdImagem());
            
        } else if (arg instanceof ArrayList<?> valoresDados && valoresDados.size() == 2) {
             // Atualiza os dados no TabuleiroView
             // O Controller já fez o casting seguro em seu update, mas repetimos por segurança.
             tabuleiroView.atualizarDados((Integer)valoresDados.get(0), (Integer)valoresDados.get(1));
        }
        
        // Repaint é necessário para atualizar o desenho do TabuleiroView
        tabuleiroView.repaint();
    }
    
    // Chamado pelo receberUpdate (Jogador)
    public void atualizarPino(int indiceJogador, int novaPosicao) {
        // Assume que TabuleiroView.setPosicaoPino() foi criado para aceitar a posição absoluta
        tabuleiroView.setPosicaoPino(indiceJogador, novaPosicao); 
    }

    // Chamado pelo receberUpdate (Jogador)
    public void atualizarPlacar(String cor, double dinheiro) {
        JLabel placar = placaresJogadores.get(cor);
        if (placar != null) {
            int numJogador = 0; 
            for(int i = 0; i < cores.length; i++) {
                if(cores[i].equals(cor)) {
                    numJogador = i + 1;
                    break;
                }
            }
            placar.setText(String.format("<html><font color='%s'>J%d (%s): </font>$%d</html>", cor, numJogador, cor, (int)dinheiro));
            painelJogadores.revalidate();
        }
    }
    
    // Chamado pelo receberUpdate (CartaSorteReves)
    public void exibirCarta(int idImagem) {
        // Assume que TabuleiroView.exibirCarta() foi criado para carregar e desenhar a imagem
        tabuleiroView.exibirCarta(idImagem);
        
        // Adiciona um pequeno delay para que o usuário possa ler a carta
        Timer timer = new Timer(3000, e -> {
            // Assume que TabuleiroView.ocultarCarta() foi criado
            tabuleiroView.ocultarCarta(); 
            ((Timer)e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    // =======================================================
    // CONFIGURAÇÃO E LISTENERS (Ações do Usuário)
    // =======================================================
    
    private void iniciarJogo() {
        numJogadores = 0;

        while (numJogadores < 2 || numJogadores > 6) {
            String input = JOptionPane.showInputDialog(
                this, "Quantos jogadores irão jogar? (2 a 6)", "Configuração Inicial", JOptionPane.QUESTION_MESSAGE
            );
            if (input == null) {
                JOptionPane.showMessageDialog(this, "Jogo cancelado.");
                System.exit(0);
            }
            try {
                numJogadores = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                numJogadores = 0;
            }
        }

        for (int i = 0; i < numJogadores; i++) {
            String cor = cores[i];
            // Passa o índice + 1 como número do jogador (1-based)
            controller.adicionarJogador(i + 1, cor); 
            
            // Adiciona placar para o novo jogador
            JLabel placar = new JLabel(String.format("<html><font color='%s'>J%d (%s): </font>$4000</html>", cor, i + 1, cor));
            placar.setForeground(corPara(cor));
            placaresJogadores.put(cor, placar);
            painelJogadores.add(placar);
            painelJogadores.revalidate();
            
            System.out.println("Jogador " + (i + 1) + " será " + cor + ".");
        }
        
        tabuleiroView.inicializarPinos(numJogadores);

        lblStatus.setText("Jogo iniciado! Vez do jogador " + controller.getCorJogadorAtual());
        tabuleiroView.setCorJogadorAtual(corPara(controller.getCorJogadorAtual()));
    }
    
    private void configurarListeners() {

        btnLancarDados.addActionListener(e -> {
            String corAtual = controller.getCorJogadorAtual(); 
            
            // 1. Lógica de Prisão (Controller)
            if (controller.verificaPrisao()) {
                // Oferece a opção de usar a carta
                controller.tentarUsarCartaSaidaLivre(); 
                
                // Se ainda estiver preso, processa a rodada (lança dados ou paga multa)
                if (controller.verificaPrisao()) {
                    lblStatus.setText("Jogador " + corAtual + " preso! Tentando sair...");
                    controller.processarPrisao(); 
                }
                
                // A atualização do estado é feita via Observer. Passa para a próxima rodada.
                this.proximaRodada();
                return;
            }
            
            // 2. Lançamento e Movimento (Controller)
            ArrayList<Integer> valores = controller.lancarDados();
            int casas = valores.get(0) + valores.get(1);
            
            // O Controller move o jogador. A View será atualizada via Observer (receberUpdate).
            controller.moverJogador(casas);
            
            // 3. Bloqueia o botão e atualiza status
            btnLancarDados.setEnabled(false);
            lblStatus.setText("Jogador " + corAtual + " moveu " + casas + " casas.");

            // Verifica falência (após todas as ações do turno)
            controller.verificarFalencia();
            if (controller.getJogadores() == 1) {
                lblStatus.setText("Jogador " + controller.getCorJogadorAtual() + " venceu!");
                btnLancarDados.setEnabled(false);
                btnProximoJogador.setEnabled(false);
            }
        });

        btnProximoJogador.addActionListener(e -> {
            this.proximaRodada();
        });
    }
    
    private void proximaRodada() {
        this.controller.proximaRodada();
        btnLancarDados.setEnabled(true);
        lblStatus.setText("Agora é a vez de " + controller.getCorJogadorAtual());
        tabuleiroView.setCorJogadorAtual(corPara(controller.getCorJogadorAtual()));
        
        // Atualiza o índice interno da View (0-based)
        // Isso é apenas para controle interno de quem é o jogador atual na View.
        this.jogadorAtual = (this.jogadorAtual + 1) % this.numJogadores;
    }

    private Color corPara(String corNome) {
        switch (corNome.toLowerCase()) {
            case "vermelho": return Color.RED;
            case "azul": return Color.BLUE;
            case "laranja": return Color.ORANGE;
            case "amarelo": return Color.YELLOW;
            case "roxo": return new Color(128, 0, 128);
            case "cinza": return Color.GRAY;
            default: return Color.BLACK;
        }
    }
    
    // 🔹 Método main para rodar o jogo
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JogoView());
    }
}