package view;
import eventos.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import controller.JogoController;
import game.observer.*;

public class JogoView extends JFrame implements Observador{

    private static final long serialVersionUID = 1L;
    private final JogoController controller = new JogoController();
    private final TabuleiroView tabuleiroView = new TabuleiroView();
    private final JButton btnLancarDados = new JButton("🎲 Lançar Dados");
    private final JButton btnProximoJogador = new JButton("➡ Próximo Jogador");
    private final JButton btnSalvamento = new JButton("Salvar Jogo 💾");
    private ArrayList<JButton> botoesJogadores = new ArrayList<>();
    private final JLabel lblStatus = new JLabel("Bem-vindo ao Banco Imobiliário!");
    private String[] cores = {"vermelho", "azul", "laranja", "amarelo", "roxo", "cinza"};
    private JPanel painelAcoesCarta = new JPanel();
    private JPanel painelBotoes = new JPanel();
    // add modo testador
    private JCheckBox chkModoTeste;
    private JComboBox<Integer> comboDado1;
    private JComboBox<Integer> comboDado2;
    private JLabel lblDado1, lblDado2;
    // modo testador Sorte ou Reves
    private JCheckBox chkModoTesteCartas;
    private JComboBox<Integer> comboCartaId;
    private JLabel lblCarta;


    private int numJogadores;
    private int jogadorAtual = 0;
    
    // 🔹 Construtor
    public JogoView() {
        super("Banco Imobiliário");

        setLayout(new BorderLayout());
        add(tabuleiroView, BorderLayout.CENTER);

        // 🔹 Painel dos botões principais
        
        painelBotoes.add(btnLancarDados);
        painelBotoes.add(btnProximoJogador);
        painelBotoes.add(btnSalvamento);
        painelBotoes.add(lblStatus);
        
        // 🔹 Painel dos botões MODO TESTADOR
        
        lblDado1 = new JLabel("Dado 1:");
        comboDado1 = new JComboBox<>(new Integer[] {1,2,3,4,5,6});
        comboDado1.setEnabled(false); // Começa desaabilitado
        
        lblDado2 = new JLabel("Dado 2:");
        comboDado2 = new JComboBox<>(new Integer[] {1,2,3,4,5,6});
        comboDado2.setEnabled(false); // Começa desaabilitado
        
        chkModoTeste = new JCheckBox("Modo Teste");
        
        painelBotoes.add(chkModoTeste);
        painelBotoes.add(lblDado1);
        painelBotoes.add(comboDado1);
        painelBotoes.add(lblDado2);
        painelBotoes.add(comboDado2);
        
     // 🔹 Painel Modo testador de cartas sorte ou reves
        lblCarta = new JLabel("Carta ID:");
        
        //
        Integer[] idsCartas = new Integer[30];
        for (int i = 0; i < 30; i++) {
            idsCartas[i] = i + 1;
        }
        
        comboCartaId = new JComboBox<>(idsCartas);
        comboCartaId.setEnabled(false); 
        
        chkModoTesteCartas = new JCheckBox("Testar Cartas");
        
        painelBotoes.add(chkModoTesteCartas);
        painelBotoes.add(lblCarta);
        painelBotoes.add(comboCartaId);

        // 🔹 Painel de ações das cartas
        painelAcoesCarta = new JPanel();
        painelAcoesCarta.setLayout(new FlowLayout());
        painelAcoesCarta.setVisible(false);

        // 🔹 Painel inferior que agrupa ambos
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(painelBotoes, BorderLayout.NORTH);
        painelInferior.add(painelAcoesCarta, BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);

        setSize(1280, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configurarListeners();

        setVisible(true);
        
        controller.adicionarObservador(this);
        this.iniciarJogo();
    }
    
    @Override
    public void atualizar(EventoJogo evento) {
        switch (evento.getTipo()) {
            case "EXIBIR_CARTA" -> {
                int tipo = (int) evento.get("tipoCarta");
                int idImg = (int) evento.get("idImagem");
                String nome = (String) evento.get("nome");
                boolean compra = (boolean) evento.get("compra");
                boolean dono = (boolean) evento.get("dono");
                exibirCarta(tipo, idImg, nome, compra, dono);
            }
            case "EXIBIR_CARTA_SORTE_REVES" -> {
                int idImg = (int) evento.get("idImagem");
                String descricao = (String) evento.get("descricao");

                // Chama seu método que exibe a carta na tela
                exibirCarta(1, idImg, descricao, true, true); // tipo=1 indica Sorte/Reves
            }
            case "LANCAR_DADOS" -> {
                int dado1 = (int) evento.get("dado1");
                int dado2 = (int) evento.get("dado2");

                tabuleiroView.atualizarDados(dado1, dado2);
                if (dado1 == dado2) {
                	btnLancarDados.setEnabled(true);
                	
                }else {
                	btnLancarDados.setEnabled(false);
                }
                
            }
            case "MOVER_JOGADOR" -> {
                int indice = (int) evento.get("indiceJogador");
                int casas = (int) evento.get("casas");
                
                tabuleiroView.moverPino(indice, casas);
            }
            case "MOVER_JOGADOR_PRISAO" -> { 
                int indice = (int) evento.get("indiceJogador");
                tabuleiroView.setarPosicaoJogadorPrisao(indice, 10);
            }
            
            case "MOVER_JOGADOR_PARTIDA" -> {
                int indice = (int) evento.get("indiceJogador");
                tabuleiroView.setarPosicaoJogadorPartida(indice);
            }
            
            case "ATUALIZAR_INFOS_JOGADOR" ->{
                double dinheiro = (double) evento.get("dinheiro");
                ArrayList<String> propriedades = (ArrayList<String>) evento.get("propriedades");
                ArrayList<Integer> companhias = (ArrayList<Integer>) evento.get("companhias");
                boolean habeas = (boolean) evento.get("temHabeasCorpus");
                String cor = (String) evento.get("cor");

                // atualizarPainelJogador(dinheiro, propriedades, companhias, habeas, cor);
                break;
            }
            default -> System.out.println("Evento não tratado: " + evento);
        }
    }

    public void exibirCarta(int tipo, int idImagem, String nome, boolean compra, boolean dono) {
        // Exibe a carta visualmente no tabuleiro
        tabuleiroView.exibirCarta(tipo, idImagem, nome);

        // Remove qualquer botão antigo antes de adicionar novos
        
        painelBotoes.setVisible(false);
        painelAcoesCarta.setVisible(true);
        
        painelAcoesCarta.removeAll();
        
        if (compra) {
	        if (tipo != 1) { // Carta do tipo "Título"
	            JButton btnComprar = new JButton("Comprar propriedade");
	            JButton btnRecusar = new JButton("Recusar compra");
	
	            // Define ações dos botões
	            btnComprar.addActionListener(e -> {
	                controller.comprarTitulo(); // chama o método do controller
	                restaurarInterface();
	            });
	
	            btnRecusar.addActionListener(e -> {
	                controller.recusarCompra();
	                restaurarInterface();
	            });
	
	            painelAcoesCarta.add(btnComprar);
	            painelAcoesCarta.add(btnRecusar);
	
	            painelAcoesCarta.revalidate();
	            painelAcoesCarta.repaint();
	
//	            btnLancarDados.setEnabled(false); // desativa jogada até escolher ação
	        } else {
	            // Outros tipos de carta (Sorte/Reves, etc.)
	            JButton btnOk = new JButton("OK");
	            btnOk.addActionListener(e -> {
	            	restaurarInterface();
	            });
	
	            painelAcoesCarta.add(btnOk);
	            painelAcoesCarta.revalidate();
	            painelAcoesCarta.repaint();
	
//	            btnLancarDados.setEnabled(false);
	        }
        } else {
        	if (dono & (tipo==2)) {
        		
        		if (controller.verificaConstrucaoHotel()) {
        			JButton btnHotel = new JButton("Comprar hotel");
    	            btnHotel.addActionListener(e -> {
    	                controller.construirHotel(); // chama o método do controller
    	                restaurarInterface();
    	            });
    	            painelAcoesCarta.add(btnHotel);
        		}
        		JButton btnCasa = new JButton("Comprar casa");
	            JButton btnRecusar = new JButton("Recusar compra");
	
	            // Define ações dos botões
	            btnCasa.addActionListener(e -> {
	                controller.construirCasa(); // chama o método do controller
	                restaurarInterface();
	            });

	            btnRecusar.addActionListener(e -> {
	                controller.recusarCompra();
	                restaurarInterface();
	            });
	            
	            painelAcoesCarta.add(btnCasa);
	            painelAcoesCarta.add(btnRecusar);
	
	            painelAcoesCarta.revalidate();
	            painelAcoesCarta.repaint();
	            
        	} else {
	        	controller.pagarAluguel();
	        	restaurarInterface();
        	}
        }
    }
    
    
    // 🔹 Método auxiliar para limpar o painel e restaurar a interface normal
    private void restaurarInterface() {
        tabuleiroView.ocultarCarta();
        painelAcoesCarta.removeAll();
        painelAcoesCarta.setVisible(false);
        painelBotoes.setVisible(true);
        painelAcoesCarta.revalidate();
        painelAcoesCarta.repaint();
    }

    
    private void iniciarJogo() {
        numJogadores = 0;
    	btnSalvamento.setEnabled(false);
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
        criarBotoesJogadores(numJogadores);

        lblStatus.setText("Jogo iniciado! Vez do jogador " + controller.getCorJogadorAtual());
    }
    private void configurarListeners() {
    	
    	// Listener do Modo Teste
    	chkModoTeste.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                // Verifica se o checkbox foi selecionado
                boolean selecionado = (e.getStateChange() == java.awt.event.ItemEvent.SELECTED);
                
                // Habilita ou desabilita os ComboBoxes
                comboDado1.setEnabled(selecionado);
                comboDado2.setEnabled(selecionado);
            }
        });
    	
    	chkModoTesteCartas.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                // Verifica se o checkbox foi selecionado
                boolean selecionado = (e.getStateChange() == java.awt.event.ItemEvent.SELECTED);
                
                // Habilita ou desabilita o ComboBox das Cartas
                comboCartaId.setEnabled(selecionado);
            }
        });

    	btnLancarDados.addActionListener(e -> {
    		btnSalvamento.setEnabled(false);
			boolean modoTeste = this.isModoTeste();
			controller.setModoTeste(modoTeste);
			
			if (modoTeste) {
				int d1 = this.getValoresDado1();
				int d2 = this.getValoresDado2();
				controller.setValoresTeste(d1, d2);
			}
			
						if (controller.verificaPrisao()) {
							lblStatus.setText("Jogador preso! Tentando sair...");
			                controller.processarPrisao(); 
			                
			                if (controller.verificaPrisao()) {
			                    controller.proximaRodada();
			                    lblStatus.setText("Agora é a vez de " + controller.getCorJogadorAtual());
			                    return; 
			                } else {
			                    lblStatus.setText("Jogador saiu da prisão! Lançando dados...");
			                }
						}
			
			boolean modoTesteCartas = this.isModoTesteCartas();
            controller.setModoTesteCartas(modoTesteCartas);
            if (modoTesteCartas) {
                int idCarta = this.getCartaIdSelecionada();
                controller.setProximaCartaTeste(idCarta);
            }

			if (controller.verificaPrisao()) {
				lblStatus.setText("Jogador preso! Tentando sair...");
				controller.processarPrisao();
				controller.proximaRodada();
				lblStatus.setText("Agora é a vez de " + controller.getCorJogadorAtual());
				return;
			}

			ArrayList<Integer> valores = controller.lancarDados();
			int casas = valores.get(0) + valores.get(1);
			controller.incrementaRodadas();
			if ((valores.get(0) == valores.get(1)) && (controller.getNumRodadas() >= 3)) {
				controller.prenderJogador();
				btnLancarDados.setEnabled(false);
				return;
			}
			controller.moverJogador(casas);

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
    	
    	btnSalvamento.addActionListener(e ->{
    		controller.salvarPartida();
    	});

        btnProximoJogador.addActionListener(e -> {
        	btnSalvamento.setEnabled(true);
        	controller.zeraRodadas();
            this.controller.proximaRodada();
            btnLancarDados.setEnabled(true);
            // Para visualizar situação do Jogador
            String cor = controller.getCorJogadorAtual();
            double dinheiro = controller.getDinheiroJogadorAtual();
            System.out.println("--- Próxima Rodada: Jogador " + cor + " | Saldo Atual: R$" + dinheiro + " ---");
            
            lblStatus.setText("Agora é a vez de " + controller.getCorJogadorAtual());
            tabuleiroView.setCorJogadorAtual(corPara(controller.getCorJogadorAtual()));
        });
    }

    public void criarBotoesJogadores(int numJogadores) {
        System.out.println("--- " + numJogadores + " ---");
        setLayout(null); // importante para posicionar manualmente

        botoesJogadores.clear();

        for (int i = 0; i < numJogadores; i++) {
            JButton btn = new JButton("J" + (i + 1));

            btn.setBounds(130, 250 + (i * 60), 240, 50); // mesma posição desenhada
            btn.setOpaque(false);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(true);

            final int jogadorIndex = i;

            //btn.addActionListener(e -> abrirJanelaJogador(jogadorIndex));
            
            botoesJogadores.add(btn);
            add(btn);
        }

        repaint();
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
    
 // 🔹 Getters para o controller
    public boolean isModoTeste() {
    	return chkModoTeste.isSelected();
    }
    
    public int getValoresDado1() {
    	return (Integer) comboDado1.getSelectedItem();
    }
    
    public int getValoresDado2() {
    	return (Integer) comboDado2.getSelectedItem();
    }
    
    public JCheckBox getChkModoTeste() {
        return chkModoTeste;
    }

    public JComboBox<Integer> getComboDado1() {
        return comboDado1;
    }

    public JComboBox<Integer> getComboDado2() {
        return comboDado2;
    }
    
    // 🔹 Métodos para testador cartas sorte ou reves
	
    public boolean isModoTesteCartas() {
        return chkModoTesteCartas.isSelected();
    }
    
    public int getCartaIdSelecionada() {
        return (Integer) comboCartaId.getSelectedItem();
    }
    
    public JCheckBox getChkModoTesteCartas() {
        return chkModoTesteCartas;
    }
    
    public JComboBox<Integer> getComboCartaId() {
        return comboCartaId;
    }
   
    
    // 🔹 Método main para rodar o jogo
	
	  public static void main(String[] args) { SwingUtilities.invokeLater(() -> new
	  JogoView()); }
	 
	 
}
