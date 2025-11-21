package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.imageio.ImageIO;

public class InfoJogadorView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel lblCor;
    private JLabel lblDinheiro;
    private JLabel lblHabeas;

    private JTextArea txtPropriedades;
    private JTextArea txtCompanhias;

    // Painéis de imagens
    private JPanel painelImagemPropriedades;
    private JPanel painelImagemCompanhias;

    public InfoJogadorView() {
        super("Informações do Jogador");
        setSize(700, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Painel raiz com scroll
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ================================
        //   PAINEL SUPERIOR FIXO
        // ================================
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblCor = new JLabel("Cor: -");
        lblCor.setFont(new Font("Arial", Font.BOLD, 16));
        lblCor.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblDinheiro = new JLabel("Dinheiro: -");
        lblDinheiro.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDinheiro.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblHabeas = new JLabel("Habeas Corpus: -");
        lblHabeas.setAlignmentX(Component.LEFT_ALIGNMENT);

        // FIXA LARGURA DOS LABELS
        lblCor.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblCor.getPreferredSize().height));
        lblDinheiro.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblDinheiro.getPreferredSize().height));
        lblHabeas.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblHabeas.getPreferredSize().height));

        painelTopo.add(lblCor);
        painelTopo.add(lblDinheiro);
        painelTopo.add(lblHabeas);

        painel.add(painelTopo);
        painel.add(Box.createVerticalStrut(10));

        // ================================
        //   TEXTOS DE PROPRIEDADES
        // ================================
        txtPropriedades = new JTextArea(5, 20);
        txtPropriedades.setEditable(false);
        txtPropriedades.setBorder(BorderFactory.createTitledBorder("Propriedades"));
        txtPropriedades.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(txtPropriedades);

        // ================================
        //   IMAGENS DE PROPRIEDADES
        // ================================
        painelImagemPropriedades = new JPanel(new GridLayout(0, 4, 5, 5)); // 4 CARTAS POR LINHA
        painelImagemPropriedades.setBorder(BorderFactory.createTitledBorder("Cartas de Propriedade"));
        painelImagemPropriedades.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(painelImagemPropriedades);

        painel.add(Box.createVerticalStrut(10));

        // ================================
        //   TEXTOS DE COMPANHIAS
        // ================================
        txtCompanhias = new JTextArea(5, 20);
        txtCompanhias.setEditable(false);
        txtCompanhias.setBorder(BorderFactory.createTitledBorder("Companhias"));
        txtCompanhias.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(txtCompanhias);

        // ================================
        //   IMAGENS DE COMPANHIAS
        // ================================
        painelImagemCompanhias = new JPanel(new GridLayout(0, 4, 5, 5)); // 4 CARTAS POR LINHA
        painelImagemCompanhias.setBorder(BorderFactory.createTitledBorder("Cartas de Companhia"));
        painelImagemCompanhias.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(painelImagemCompanhias);


        add(new JScrollPane(painel), BorderLayout.CENTER);
    }

    public void atualizar(String cor, double dinheiro, List<String> propriedades, List<Integer> companhias, boolean habeas) {
        lblCor.setText("Cor: " + cor);
        lblDinheiro.setText("Dinheiro: R$ " + dinheiro);
        lblHabeas.setText("Habeas Corpus: " + (habeas ? "Sim" : "Não"));


        // ================================
        //   PROPRIEDADES
        // ================================
        txtPropriedades.setText("");
        painelImagemPropriedades.removeAll();

        for (String nome : propriedades) {
            txtPropriedades.append("• " + nome + "\n");

            String caminho = "/view/imagens/territorios/" + nome + ".png";

            try {
                BufferedImage img = ImageIO.read(getClass().getResource(caminho));
                Image scaled = img.getScaledInstance(110, 170, Image.SCALE_SMOOTH);
                painelImagemPropriedades.add(new JLabel(new ImageIcon(scaled)));
            } catch (Exception e) {
                System.err.println("Falha ao carregar propriedade: " + caminho);
            }
        }

        // ================================
        //   COMPANHIAS
        // ================================
        txtCompanhias.setText("");
        painelImagemCompanhias.removeAll();

        for (int id : companhias) {
            txtCompanhias.append("• Companhia " + id + "\n");

            String caminho = "/view/imagens/companhias/company" + id + ".png";

            try {
                BufferedImage img = ImageIO.read(getClass().getResource(caminho));
                Image scaled = img.getScaledInstance(110, 170, Image.SCALE_SMOOTH);
                painelImagemCompanhias.add(new JLabel(new ImageIcon(scaled)));
            } catch (Exception e) {
                System.err.println("Falha ao carregar companhia: " + caminho);
            }
        }

        revalidate();
        repaint();
    }
}
