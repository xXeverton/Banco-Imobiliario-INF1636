package view;

import controller.JogoController;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Point;


public class TabuleiroView extends JPanel {
    private static final long serialVersionUID = 1L;
    private BufferedImage imagemTabuleiro;
    private BufferedImage dado1Img, dado2Img;
    private List<BufferedImage> imagensPinos;
    private List<Point> posicoesPinos;
    private int[] posicaoAtual;
    private Color corJogadorAtual = Color.RED;
    private BufferedImage imagemCartaSorteada; // NOVO CAMPO: Imagem da carta sorteada
    private boolean exibirCarta = false;
    private Map<String, BufferedImage> imagensPorCor;
    private JogoController controller = new JogoController();
    
    private int valorDado1 = 1;
    private int valorDado2 = 1;

    public TabuleiroView() {
        setBackground(new Color(230, 230, 230));
        carregarImagens();
    }

    private void carregarImagens() {
        try {
            imagemTabuleiro = ImageIO.read(getClass().getResource("/view/imagens/tabuleiro.png"));
            dado1Img = ImageIO.read(getClass().getResource("/view/imagens/dados/die_face_1.png"));
            dado2Img = ImageIO.read(getClass().getResource("/view/imagens/dados/die_face_1.png"));
            imagensPorCor = new HashMap<>();
            imagensPorCor.put("vermelho", ImageIO.read(getClass().getResource("/view/imagens/pinos/pin0.png")));
            imagensPorCor.put("azul", ImageIO.read(getClass().getResource("/view/imagens/pinos/pin1.png")));
            imagensPorCor.put("laranja", ImageIO.read(getClass().getResource("/view/imagens/pinos/pin2.png")));
            imagensPorCor.put("amarelo", ImageIO.read(getClass().getResource("/view/imagens/pinos/pin3.png")));
            imagensPorCor.put("roxo", ImageIO.read(getClass().getResource("/view/imagens/pinos/pin4.png")));
            imagensPorCor.put("cinza", ImageIO.read(getClass().getResource("/view/imagens/pinos/pin5.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao carregar imagens: " + e.getMessage());
            imagemTabuleiro = null;
        }
    }
    
    public void inicializarPinos(int numJogadores) {
        imagensPinos = new ArrayList<>();
        posicoesPinos = new ArrayList<>();

        // posição base (canto inferior direito, casa inicial)
        int baseX = 1110;
        int baseY = 575;

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

    public void setarPosicaoJogadorPrisao(int indice, int posfinal) {
    	if (posicoesPinos == null || indice >= posicoesPinos.size()) return;
    	posicaoAtual[indice] = posfinal;
    	
        int espacamento = 25;
        int linha = indice % 2;
        int coluna = indice / 2;

        int offset = posfinal % 10;
    	int x = 470;
    	int y = 570;
    	int primeiroPasso = 70;
    	int passo = 47;
        if (offset == 0) {
        } else if (offset == 1) {
            y -= primeiroPasso;
        } else {
            y -= primeiroPasso + (offset - 1) * passo;
        }
         
         x = x + (coluna * espacamento);
         y = y - (linha * espacamento);
         posicoesPinos.set(indice, new Point(x, y));
         repaint();
    }
    
    
    public void setarPosicaoJogadorPartida(int indice) {
        if (posicoesPinos == null || indice >= posicoesPinos.size()) return;

        posicaoAtual[indice] = 0; 
        
       
        int baseX = 1110;
        int baseY = 575;
        int espacamento = 25;

        int coluna = indice % 2; 
        int linha = indice / 2; 

        int x = baseX + (coluna * espacamento);
        int y = baseY - (linha * espacamento);

        posicoesPinos.set(indice, new Point(x, y));
        repaint();
    }
    
    public void moverPino(int indice, int casas) {
        if (posicoesPinos == null || indice >= posicoesPinos.size()) return;
        System.out.println(indice);
        int posicao = (posicaoAtual[indice] + casas) % 40;
        posicaoAtual[indice] = posicao;

        int lado = posicao / 10;
        int offset = posicao % 10;

        int x = 0, y = 0;
        int primeiroPasso = 0, passo = 0;

        // Define as bases de cada lado conforme suas anotações
        switch (lado) {
            case 0: // Base inferior (direita → esquerda)
                x = 1110;
                y = 575;
                primeiroPasso = 70;
                passo = 60;
                if (offset == 0) {
                } else if (offset == 1) {
                    x -= primeiroPasso;
                } else {
                    x -= primeiroPasso + (offset - 1) * passo;
                }
                break;

            case 1: // Lado esquerdo (baixo → cima)
                x = 470;
                y = 570;
                primeiroPasso = 70;
                passo = 45;
                if (offset == 0) {
                } else if (offset == 1) {
                    y -= primeiroPasso;
                } else {
                    y -= primeiroPasso + (offset - 1) * passo;
                }
                break;

            case 2: // Topo (esquerda → direita)
                x = 485;
                y = 90;
                primeiroPasso = 75;
                passo = 60;
                if (offset == 0) {
                } else if (offset == 1) {
                    x += primeiroPasso;
                } else {
                    x += primeiroPasso + (offset - 1) * passo;
                }
                break;

            case 3: // Lado direito (cima → baixo)
                x = 1115;
                y = 80;
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
        repaint();
    }
    
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
    
    public void exibirCarta(int tipo,int idImagem, String nome) { // Recebe int para Sorte/Reves ou Companhia e Nome para propriedade
    	if (tipo == 1) {
	        try {
	            // Carrega a imagem da carta Sorte/Revés (chance1.png a chance30.png)
	            imagemCartaSorteada = ImageIO.read(getClass().getResource("/view/imagens/sorteReves/chance" + idImagem + ".png"));
	            exibirCarta = true;
	            repaint();
	        } catch (IOException | IllegalArgumentException e) {
	            System.err.println("Erro ao carregar imagem da carta ID " + idImagem + ": " + e.getMessage());
	            imagemCartaSorteada = null;
	        }
    	} else if(tipo == 2) {
	        try {
	            // Carrega a imagem da carta Propriedade (nome da propriedade)
	            imagemCartaSorteada = ImageIO.read(getClass().getResource("/view/imagens/territorios/" + nome + ".png"));
	            exibirCarta = true;
	            repaint();
	        } catch (IOException | IllegalArgumentException e) {
	            System.err.println("Erro ao carregar imagem de carta com nome " + nome + ": " + e.getMessage());
	            imagemCartaSorteada = null;
	        } 
    	} else {
	        try {
	            // Carrega a imagem da carta Companhia (company1.png a company6.png)
	            imagemCartaSorteada = ImageIO.read(getClass().getResource("/view/imagens/companhias/company" + idImagem + ".png"));
	            exibirCarta = true;
	            repaint();
	        } catch (IOException | IllegalArgumentException e) {
	            System.err.println("Erro ao carregar imagem da carta ID " + idImagem + ": " + e.getMessage());
	            imagemCartaSorteada = null;
	        }
    	}
    }
    
    public void ocultarCarta() {
        exibirCarta = false;
        repaint();
    }
    
    public void setCorJogadorAtual(Color cor) {
        this.corJogadorAtual = cor;
        repaint(); // redesenha a área dos dados com a nova cor
    }
    
    public void removerPinoJogador(int indice) {
        if (imagensPinos == null || posicoesPinos == null) return;
        if (indice < 0 || indice >= imagensPinos.size()) return;

        imagensPinos.remove(indice);
        posicoesPinos.remove(indice);

        // Recria o array de posições lógicas
        int[] novo = new int[posicaoAtual.length - 1];
        for (int i = 0, j = 0; i < posicaoAtual.length; i++) {
            if (i != indice) {
                novo[j++] = posicaoAtual[i];
            }
        }
        posicaoAtual = novo;
        repaint();
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // --- Desenha o tabuleiro ---
        if (imagemTabuleiro != null) {
            int x = (getWidth() - 500);
            int y = (getHeight() - 90);
            g2d.drawImage(imagemTabuleiro, 450, 45, x, y, this);
        }
        // --- Desenha a área dos dados à esquerda ---
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRoundRect(130, 60, 240, 120, 20, 20);
        
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(corJogadorAtual);
        g2d.drawRoundRect(130, 60, 240, 120, 20, 20);
        
        // --- Desenha os dados ---
        if (dado1Img != null && dado2Img != null) {
            g2d.drawImage(dado1Img, 160, 80, 80, 80, this);
            g2d.drawImage(dado2Img, 260, 80, 80, 80, this);
        }

        // --- Desenha os pinos ---
        if (imagensPinos != null) {
            for (int i = 0; i < imagensPinos.size(); i++) {
            	String corJogador = controller.getCorJogadores().get(i);
            	BufferedImage pino = imagensPorCor.get(corJogador);
                if (pino != null && posicoesPinos != null) {
                    Point p = posicoesPinos.get(i);
                    g2d.drawImage(pino, p.x, p.y, 30, 30, this);
                }
            }
        }
        
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
