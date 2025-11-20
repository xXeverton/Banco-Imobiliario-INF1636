package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.imageio.ImageIO;

public class InfoJogadorView extends JFrame {

    private JLabel lblCor;
    private JLabel lblDinheiro;
    private JTextArea txtPropriedades;
    private JTextArea txtCompanhias;
    private JLabel lblHabeas;

    // 🔹 Novos painéis para exibir as imagens
    private JPanel painelImagemPropriedades;
    private JPanel painelImagemCompanhias;

    public InfoJogadorView() {
        super("Informações do Jogador");
        setSize(450, 650);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblCor = new JLabel("Cor: -");
        lblCor.setFont(new Font("Arial", Font.BOLD, 16));

        lblDinheiro = new JLabel("Dinheiro: -");
        lblDinheiro.setFont(new Font("Arial", Font.PLAIN, 16));

        lblHabeas = new JLabel("Habeas Corpus: -");

        // Painéis de texto
        txtPropriedades = new JTextArea(5, 20);
        txtPropriedades.setEditable(false);
        txtPropriedades.setBorder(BorderFactory.createTitledBorder("Propriedades"));

        txtCompanhias = new JTextArea(5, 20);
        txtCompanhias.setEditable(false);
        txtCompanhias.setBorder(BorderFactory.createTitledBorder("Companhias"));

        // 🔹 Painéis de imagem
        painelImagemPropriedades = new JPanel(new GridLayout(0, 1));
        painelImagemPropriedades.setBorder(BorderFactory.createTitledBorder("Cartas de Propriedade"));

        painelImagemCompanhias = new JPanel(new GridLayout(0, 1));
        painelImagemCompanhias.setBorder(BorderFactory.createTitledBorder("Cartas de Companhia"));

        // Montagem da janela
        painel.add(lblCor);
        painel.add(lblDinheiro);
        painel.add(lblHabeas);
        painel.add(Box.createVerticalStrut(10));

        painel.add(txtPropriedades);
        painel.add(painelImagemPropriedades);
        painel.add(Box.createVerticalStrut(10));

        painel.add(txtCompanhias);
        painel.add(painelImagemCompanhias);

        add(new JScrollPane(painel), BorderLayout.CENTER);
    }

    public void atualizar(String cor, double dinheiro,
                          List<String> propriedades,
                          List<Integer> companhias,
                          boolean habeas) {

        lblCor.setText("Cor: " + cor);
        lblDinheiro.setText("Dinheiro: R$ " + dinheiro);
        lblHabeas.setText("Habeas Corpus: " + (habeas ? "Sim" : "Não"));

        // -------- PROPRIEDADES --------
        txtPropriedades.setText("");
        painelImagemPropriedades.removeAll();

        for (String nome : propriedades) {
            txtPropriedades.append("• " + nome + "\n");

            String caminho = "/view/imagens/territorios/" + nome + ".png";

            try {
                BufferedImage img = ImageIO.read(getClass().getResource(caminho));
                Image scaled = img.getScaledInstance(150, 240, Image.SCALE_SMOOTH);
                painelImagemPropriedades.add(new JLabel(new ImageIcon(scaled)));

            } catch (Exception e) {
                System.err.println("Falha ao carregar propriedade: " + caminho);
            }
        }

        // -------- COMPANHIAS --------
        txtCompanhias.setText("");
        painelImagemCompanhias.removeAll();

        for (int id : companhias) {
            txtCompanhias.append("• Companhia " + id + "\n");

            String caminho = "/view/imagens/companhias/company" + id + ".png";

            try {
                BufferedImage img = ImageIO.read(getClass().getResource(caminho));
                Image scaled = img.getScaledInstance(150, 240, Image.SCALE_SMOOTH);
                painelImagemCompanhias.add(new JLabel(new ImageIcon(scaled)));

            } catch (Exception e) {
                System.err.println("Falha ao carregar companhia: " + caminho);
            }
        }

        revalidate();
        repaint();
    }
}
