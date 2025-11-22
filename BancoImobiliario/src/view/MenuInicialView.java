package view;

import controller.JogoController;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MenuInicialView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JogoController controller = new JogoController();

    public MenuInicialView() {
        super("Banco Imobiliário - Menu Inicial");
        setLayout(new GridBagLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel titulo = new JLabel("Banco Imobiliário", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnNovoJogo = new JButton("Novo Jogo");
        JButton btnCarregarJogo = new JButton("Carregar Jogo");

        btnNovoJogo.addActionListener(e -> {

            dispose(); 
            configurarNovoJogo(); 
        });

        btnCarregarJogo.addActionListener(e -> {
            carregarJogo();
        });

        panel.add(titulo);
        panel.add(btnNovoJogo);
        panel.add(btnCarregarJogo);
        add(panel);
        
        setVisible(true);
    }

    private void carregarJogo() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Carregar Partida Salva");
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivo de Texto (*.txt)", "txt"));

        int resposta = fc.showOpenDialog(this);
        if (resposta == JFileChooser.APPROVE_OPTION) {
            File arquivo = fc.getSelectedFile();
            try {
                controller.carregarPartidaArquivo(arquivo); 
                
                dispose();
                abrirJogoView();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar: " + ex.getMessage());
            }
        }
    }

    private void configurarNovoJogo() {
        controller.resetarJogo(); 

        String input = JOptionPane.showInputDialog(this, "Quantos jogadores? (2 a 6)");
        if (input == null) return;
        
        try {
            int num = Integer.parseInt(input);
            if (num < 2 || num > 6) throw new NumberFormatException();
            
            String[] cores = {"vermelho", "azul", "laranja", "amarelo", "roxo", "cinza"};
            for (int i = 0; i < num; i++) {
                controller.adicionarJogador(i + 1, cores[i]);
            }
            
            abrirJogoView();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número inválido!");
        }
    }

    private void abrirJogoView() {
    	ArrayList<String> cores = controller.getCorJogadores();
        SwingUtilities.invokeLater(() -> new JogoView(cores));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuInicialView());
    }
}