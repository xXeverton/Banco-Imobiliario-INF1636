package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Random;
import game.Tabuleiro;

public class TabuleiroView extends JPanel {
    private static final long serialVersionUID = 1L;
    private BufferedImage imagemTabuleiro;
    private BufferedImage dado1Img, dado2Img;
    private Tabuleiro tabuleiro;
    private int valorDado1 = 1;
    private int valorDado2 = 1;

    public TabuleiroView() {
        this.tabuleiro = new Tabuleiro();
        setBackground(new Color(230, 230, 230));
        carregarImagens();
    }

    private void carregarImagens() {
        try {
            imagemTabuleiro = ImageIO.read(getClass().getResource("/view/imagens/tabuleiro.png"));
            dado1Img = ImageIO.read(getClass().getResource("/view/imagens/dados/die_face_1.png"));
            dado2Img = ImageIO.read(getClass().getResource("/view/imagens/dados/die_face_1.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao carregar imagens: " + e.getMessage());
            imagemTabuleiro = null;
        }
    }

    /** Simula o lançamento dos dois dados */
    public void jogarDados() {
        Random rand = new Random();
        valorDado1 = rand.nextInt(6) + 1;
        valorDado2 = rand.nextInt(6) + 1;
        atualizarImagensDados();
        repaint();
    }

    private void atualizarImagensDados() {
        try {
            dado1Img = ImageIO.read(getClass().getResource("/view/imagens/dados/die_face_" + valorDado1 + ".png"));
            dado2Img = ImageIO.read(getClass().getResource("/view/imagens/dados/die_face_" + valorDado2 + ".png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao atualizar imagem dos dados: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // --- Desenha o tabuleiro ---
        if (imagemTabuleiro != null) {
            int x = (getWidth() - 550);
            int y = (getHeight() - 150);
            g2d.drawImage(imagemTabuleiro, 500, 60, x, y, this);

            
            
            // --- Desenha a área dos dados à esquerda ---
            int dadosAreaX = (getWidth() - 250);
            int dadosAreaY = (getHeight() - 300);

            g2d.setColor(new Color(240, 240, 240));
            g2d.fillRoundRect(0, 0, 200, 200, 20, 20);
            g2d.setColor(Color.BLUE);
            g2d.drawRoundRect(0, 0, 200, 200, 20, 20);

            if (dado1Img != null && dado2Img != null) {
                int tamanhoDado = 80;
                g2d.drawImage(dado1Img, 0, 0, tamanhoDado, tamanhoDado, this);
                g2d.drawImage(dado2Img, 100, 0, tamanhoDado, tamanhoDado, this);
            }

            // --- Texto "Jogar Dados" ---
//            g2d.setFont(new Font("Arial", Font.BOLD, 16));
//            g2d.drawString("Jogar Dados: Espaço", dadosAreaX + 20, dadosAreaY + 180);
        } else {
            g2d.setColor(Color.RED);
            g2d.drawString("Imagem do tabuleiro não encontrada!", 50, 50);
        }

        g2d.dispose();
    }

    // =============== MAIN ===============
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Banco Imobiliário");
            TabuleiroView painel = new TabuleiroView();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1280, 800);
            frame.setLayout(new BorderLayout());
            frame.add(painel, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Pressione ESPAÇO para jogar os dados
            frame.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                        painel.jogarDados();
                    }
                }
            });
        });
    }
}
