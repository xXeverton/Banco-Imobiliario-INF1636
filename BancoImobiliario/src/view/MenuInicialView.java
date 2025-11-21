package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class MenuInicialView extends JFrame {

    // Evita warning de serialização
    private static final long serialVersionUID = 1L;

    public MenuInicialView() {
        super("Banco Imobiliário - Menu Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Estilo básico
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnNovoJogo = new JButton("Novo Jogo");
        JButton btnCarregar = new JButton("Carregar Jogo");
        JButton btnSair = new JButton("Sair");

        // Ação: Novo Jogo
        btnNovoJogo.addActionListener(e -> {
            this.dispose(); // Fecha o menu
            iniciarNovoJogo();
        });

        // Ação: Carregar Jogo
        btnCarregar.addActionListener(e -> {
            carregarJogo();
        });

        // Ação: Sair
        btnSair.addActionListener(e -> System.exit(0));

        panel.add(btnNovoJogo);
        panel.add(btnCarregar);
        panel.add(btnSair);

        add(panel);
        
        // Importante: torna a janela visível
        setVisible(true);
    }

    private void iniciarNovoJogo() {
        // Inicia a View principal do jogo
        SwingUtilities.invokeLater(() -> new JogoView());
    }

    private void carregarJogo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Carregar Jogo Salvo");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto (.txt)", "txt"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            // 1. Fecha o menu atual
            this.dispose(); 
            
            // 2. Cria o jogo em uma thread segura
            SwingUtilities.invokeLater(() -> {
                JogoView jogoView = new JogoView(); 
                
                // 3. Manda o controller do novo jogo carregar o arquivo
                jogoView.getController().carregarPartidaPeloArquivo(file);
            });
        }
    }

    // Ponto de entrada do programa
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuInicialView::new);
    }
}