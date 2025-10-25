package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import controller.JogoController;

public class JogoView extends JFrame {

    private static final long serialVersionUID = 1L;
    private final JogoController controller = new JogoController();
    private final TabuleiroView tabuleiroView = new TabuleiroView();
    private final JButton btnLancarDados = new JButton("🎲 Lançar Dados");
    private final JButton btnProximoJogador = new JButton("➡ Próximo Jogador");
    private final JLabel lblStatus = new JLabel("Bem-vindo ao Banco Imobiliário!");
    private String[] cores = {"vermelho", "azul", "laranja", "amarelo", "roxo", "cinza"};

    private int numJogadores;
    private int jogadorAtual = 0;
    
    // 🔹 Construtor
    public JogoView() {
        super("Banco Imobiliário");

        setLayout(new BorderLayout());
        add(tabuleiroView, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnLancarDados);
        painelBotoes.add(btnProximoJogador);
        painelBotoes.add(lblStatus);
        add(painelBotoes, BorderLayout.SOUTH);

        setSize(1280, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configurarListeners();

        setVisible(true);
        
        this.iniciarJogo();
    }
    
    private void iniciarJogo() {
        numJogadores = 0;

        // Caixa de diálogo gráfica para escolher quantidade
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

        // Adiciona jogadores com cores fixas
        for (int i = 0; i < numJogadores; i++) {
            String cor = cores[i];
            controller.adicionarJogador(i + 1, cor);
            System.out.println("Jogador " + (i + 1) + " será " + cor + ".");// <--
        }
        // 🔹 Inicializa pinos no tabuleiro
        tabuleiroView.inicializarPinos(numJogadores);

        lblStatus.setText("Jogo iniciado! Vez do jogador " + controller.getCorJogadorAtual());
    }
    private void configurarListeners() {

        btnLancarDados.addActionListener(e -> {
            // Jogador atual tenta jogar
            if (controller.verificaPrisao()) {
                lblStatus.setText("Jogador preso! Tentando sair...");
                controller.processarPrisao();
                controller.proximaRodada();
                lblStatus.setText("Agora é a vez de " + controller.getCorJogadorAtual());
                return;
            }
            // Movimento no tabuleiro
            ArrayList<Integer> valores = controller.lancarDados();
            int casas = valores.get(0) + valores.get(1);
            controller.moverJogador(casas);
            tabuleiroView.moverPino(jogadorAtual, casas);
            
            // Atualiza o tabuleiro graficamente
            tabuleiroView.atualizarDados(valores.get(0), valores.get(1));
            btnLancarDados.setEnabled(false);

            // Verifica falência e fim de jogo
            controller.verificarFalencia();
            if (controller.getJogadores() == 1) {
                lblStatus.setText("Jogador " + controller.getCorJogadorAtual() + " venceu!");
                btnLancarDados.setEnabled(false);
                btnProximoJogador.setEnabled(false);
                return;
            }

            lblStatus.setText("Jogador moveu " + casas + " casas.");
            jogadorAtual = (jogadorAtual + 1) % numJogadores;
        });

        btnProximoJogador.addActionListener(e -> {
            this.controller.proximaRodada();
            btnLancarDados.setEnabled(true);
            lblStatus.setText("Agora é a vez de " + controller.getCorJogadorAtual());
            tabuleiroView.setCorJogadorAtual(corPara(controller.getCorJogadorAtual()));
        });
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
