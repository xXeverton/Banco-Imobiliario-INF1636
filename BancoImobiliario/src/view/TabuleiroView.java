package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class TabuleiroView extends JPanel {
    private static final long serialVersionUID = 1L;
    private BufferedImage imagemTabuleiro;
    private BufferedImage dado1Img, dado2Img;
    private BufferedImage imagemCartaSorteada; // NOVO CAMPO: Imagem da carta sorteada
    private boolean exibirCarta = false;      // NOVO CAMPO: Flag para desenhar a carta

    private List<BufferedImage> imagensPinos;
    private List<Point> posicoesPinos;
    private int[] posicaoAtual; // Armazena a posição absoluta (0 a 39)
    private Color corJogadorAtual = Color.RED;
    
    private int valorDado1 = 1;
    private int valorDado2 = 1;

    public TabuleiroView() {
        setBackground(new Color(230, 230, 230));
        carregarImagens();
    }

    private void carregarImagens() {
        try {
            // Carrega imagens iniciais
            imagemTabuleiro = ImageIO.read(getClass().getResource("/view/imagens/tabuleiro.png"));
            dado1Img = ImageIO.read(getClass().getResource("/view/imagens/dados/die_face_1.png"));
            dado2Img = ImageIO.read(getClass().getResource("/view/imagens/dados/die_face_1.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao carregar imagens: " + e.getMessage());
            imagemTabuleiro = null;
        }
    }
    
    public void inicializarPinos(int numJogadores) {
        imagensPinos = new ArrayList<>();
        posicoesPinos = new ArrayList<>();

        // posição base (canto inferior direito, casa inicial)
        int baseX = 985;
        int baseY = 605;

        int espacamento = 25;

        for (int i = 0; i < numJogadores; i++) {
            try {
                BufferedImage img = ImageIO.read(getClass().getResource("/view/imagens/pinos/pin" + i + ".png"));
                imagensPinos.add(img);
            } catch (IOException e) {
                System.err.println("Erro ao carregar pino " + i);
                imagensPinos.add(null);
            }

            // define a coluna (0 ou 1) e a linha (0, 1, 2)
            int coluna = i % 2;     // 0 = esquerda, 1 = direita
            int linha = i / 2;      // muda a cada 2 pinos

            int x = baseX + (coluna * espacamento);
            int y = baseY - (linha * espacamento);

            posicoesPinos.add(new Point(x, y));
        }

        posicaoAtual = new int[numJogadores];
        for (int i = 0; i < numJogadores; i++) {
            posicaoAtual[i] = 0; // todos começam na casa inicial
        }

        repaint();
    }

    /**
     * NOVO: Recebe a posição ABSOLUTA do Model (via Observer) e atualiza o pino.
     * @param indice Índice do pino (0-based).
     * @param novaPosicao Posição absoluta (0 a 39).
     */
    public void setPosicaoPino(int indice, int novaPosicao) {
        if (posicoesPinos == null || indice >= posicoesPinos.size()) return;

        // Atualiza a posição no array interno
        posicaoAtual[indice] = novaPosicao;

        // Calcula as novas coordenadas X/Y
        calcularCoordenadasPino(indice, novaPosicao);
        
        repaint();
    }

    /**
     * MoverPino antigo, reescrito para calcular as coordenadas X/Y
     * baseado na posição absoluta.
     */
    private void calcularCoordenadasPino(int indice, int posicao) {
        // Lógica de cálculo X/Y transferida e adaptada para usar 'posicao' absoluta
        
        int lado = posicao / 10;
        int offset = posicao % 10;

        int x = 0, y = 0;
        int primeiroPasso = 0, passo = 0;

        // Define as bases de cada lado conforme suas anotações
        switch (lado) {
            case 0: // Base inferior (direita → esquerda)
                x = 985;
                y = 605;
                primeiroPasso = 70;
                passo = 55;
                if (offset == 0) {
                } else if (offset == 1) {
                    x -= primeiroPasso;
                } else {
                    x -= primeiroPasso + (offset - 1) * passo;
                }
                break;

            case 1: // Lado esquerdo (baixo → cima)
                x = 380;
                y = 600;
                primeiroPasso = 70;
                passo = 47;
                if (offset == 0) {
                } else if (offset == 1) {
                    y -= primeiroPasso;
                } else {
                    y -= primeiroPasso + (offset - 1) * passo;
                }
                break;

            case 2: // Topo (esquerda → direita)
                x = 405;
                y = 110;
                primeiroPasso = 70;
                passo = 55;
                if (offset == 0) {
                } else if (offset == 1) {
                    x += primeiroPasso;
                } else {
                    x += primeiroPasso + (offset - 1) * passo;
                }
                break;

            case 3: // Lado direito (cima → baixo)
                x = 985;
                y = 100;
                primeiroPasso = 60;
                passo = 46;
                if (offset == 0) {
                } else if (offset == 1) {
                    y += primeiroPasso;
                } else {
                    y += primeiroPasso + (offset - 1) * passo;
                }
                break;
        }

        // Ajuste visual de sobreposição dos pinos
        int espacamento = 25;
        int linha;
        int coluna;

        // Alterna orientação dos offsets conforme o lado
        if (lado == 1 || lado == 3) {
            linha = indice % 2;
            coluna = indice / 2;
        } else {
            linha = indice / 2;
            coluna = indice % 2;
        }
        x = x + (coluna * espacamento);
        y = y - (linha * espacamento);
        
        posicoesPinos.set(indice, new Point(x, y));
    }
    
    // Método antigo (moverPino) removido ou adaptado.
    // O código anterior contia:
    // public void moverPino(int indice, int casas) { 
    //     // ... cálculo interno de posicao ...
    //     posicaoAtual[indice] = posicao;
    //     calcularCoordenadasPino(indice, posicao);
    //     repaint();
    // }
    // O método 'setPosicaoPino' o substitui e é o que o Observer deve chamar.

    /** Simula o lançamento dos dois dados */
    public void atualizarDados(int dado1, int dado2) {
        valorDado1 = dado1;
        valorDado2 = dado2;
        atualizarImagensDados();
        repaint();
    }

    private void atualizarImagensDados() {
        try {
            dado1Img = ImageIO.read(getClass().getResource("/view/imagens/dados/die_face_" + valorDado1 + ".png"));
            dado2Img = ImageIO.read(getClass().getResource("/view/imagens/dados/die_face_" + valorDado2 + ".png"));
        } catch (IOException e) {
            System.err.println("Erro ao atualizar imagem dos dados: " + e.getMessage());
        }
    }
    
    public void setCorJogadorAtual(Color cor) {
        this.corJogadorAtual = cor;
        repaint(); // redesenha a área dos dados com a nova cor
    }
    
    /**
     * NOVO: Carrega e marca a carta para ser exibida.
     * @param idImagem ID da carta (1 a 30).
     */
    public void exibirCarta(int idImagem) {
        try {
            // Carrega a imagem da carta Sorte/Revés (chance1.png a chance30.png)
            imagemCartaSorteada = ImageIO.read(getClass().getResource("/view/imagens/sorteReves/chance" + idImagem + ".png"));
            exibirCarta = true;
            repaint();
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao carregar imagem da carta ID " + idImagem + ": " + e.getMessage());
            imagemCartaSorteada = null;
        }
    }

    /**
     * NOVO: Oculta a carta (após o Timer da JogoView expirar).
     */
    public void ocultarCarta() {
        exibirCarta = false;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // --- Desenha o tabuleiro ---
        if (imagemTabuleiro != null) {
            int x = (getWidth() - 425);
            int y = (getHeight() - 140);
            g2d.drawImage(imagemTabuleiro, 370, 60, x, y, this);
        }
        // --- Desenha a área dos dados à esquerda ---
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRoundRect(60, 60, 240, 120, 20, 20);
        
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(corJogadorAtual);
        g2d.drawRoundRect(60, 60, 240, 120, 20, 20);
        
        // --- Desenha os dados ---
        if (dado1Img != null && dado2Img != null) {
            g2d.drawImage(dado1Img, 90, 80, 80, 80, this);
            g2d.drawImage(dado2Img, 190, 80, 80, 80, this);
        }

        // --- Desenha os pinos ---
        if (imagensPinos != null) {
            for (int i = 0; i < imagensPinos.size(); i++) {
                BufferedImage pino = imagensPinos.get(i);
                if (pino != null && posicoesPinos != null) {
                    Point p = posicoesPinos.get(i);
                    g2d.drawImage(pino, p.x, p.y, 30, 30, this);
                }
            }
        }

        // --- NOVO: Desenha a carta no centro (Java2D) ---
        if (exibirCarta && imagemCartaSorteada != null) {
            int cardWidth = 300;
            int cardHeight = 450;
            // Centraliza a carta no painel TabuleiroView
            int x = (getWidth() - cardWidth) / 2; 
            int y = (getHeight() - cardHeight) / 2;
            
            // Desenha a imagem da carta
            g2d.drawImage(imagemCartaSorteada, x, y, cardWidth, cardHeight, this);
        }

        g2d.dispose();
    }
}