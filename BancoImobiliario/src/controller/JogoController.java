package controller;

import game.Fachada;
import game.observer.*;
import view.JogoView;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;


public class JogoController {
    private Fachada f;
    private int casas;
    private int rodadas_jogador = 0;
    private JogoView view;
    public JogoController() {
        this.f = Fachada.getInstancia();  // Singleton
    }
    
    public void setView(JogoView view) {
        this.view = view;
    }
    
    public void adicionarObservador(Observador o) {
    	this.f.adicionarObservador(o);
    }

    public void adicionarJogador(int numero, String cor) {
        f.adicionarJogador(numero, cor);
    }

    
    public int getJogadores() {
    	return f.getNumeroJogadores();
    }
    
    public String getCorJogadorAtual() {
    	return f.getCorJogadorAtual();
    }

    public ArrayList<Integer> lancarDados() {
        // REGRA: "Após um jogador começar... o botão de salvamento DEVE ser desabilitado"
        if (view != null) {
            view.setSalvarEnabled(false);
        }
        
        ArrayList<Integer> valores;
        
        // Lógica do modo teste (preservada)
        if (view != null && view.isModoTeste()) {
             valores = f.lancarDados();
        } else {
             valores = f.lancarDados();
        }
        
        System.out.println("Jogador " + f.getJogadorAtual() + " tirou " + valores.get(0) + "," + valores.get(1));
        return valores;
    }
    
    // 🔹 Métodos para modo TESTADOR 
    public void setModoTeste(boolean modo) {
    	f.setModoTeste(modo);
    }
    
    public void setValoresTeste(int d1, int d2) {
    	f.setValoresTeste(d1, d2);
    }
    
    public void setModoTesteCartas(boolean modo) {
        f.setModoTesteCartas(modo);
    }
    
    public void setProximaCartaTeste(int idCarta) {
        f.setProximaCartaTeste(idCarta);
    }

    // Deslocar o jogador da vez
    public void moverJogador(int casas) {
    	this.casas = casas;
        // Pede pra fachada mover o jogador e retorna o tipo da casa
        var tipo = f.moverJogador(casas); // Novo método na Fachada

        switch (tipo) {
            case PARTIDA:
                System.out.println("Caiu na casa de Partida. Recebe R$200.");
                f.premiacaoJogador();
                break;

            case PRISAO:
                System.out.println("Está apenas visitando a prisão.");
                break;

            case VA_PARA_PRISAO:
                prenderJogador();
                break;

            case SORTE_REVES:
                System.out.println("Comprando carta Sorte ou Revés...");
                f.notificarCartaSorteReves();
                break;

            case IMPOSTO:
                System.out.println("Pagando imposto de R$200 ao banco.");
                f.impostoJogador();
                break;

            case LUCROS_DIVIDENDOS:
                System.out.println("Recebendo dividendo de R$200 do banco.");
                f.premiacaoJogador();
                break;

            case TITULO:
            	  f.notificarCartaCasa();
                break;

            default:
                System.out.println("Casa livre, nada acontece.");
                break;
        }
    }

    public void comprarTitulo() {
    	boolean sucesso = f.processarTituloCasaAtual();
    	
        if (sucesso) {
            System.out.println("Jogador " + f.getCorJogadorAtual() + 
                               " comprou o título " + f.getNomeCasaAtual());
        } else {
            System.out.println("Jogador " + f.getCorJogadorAtual() + 
                               " não conseguiu comprar o título " + f.getNomeCasaAtual());
        }
    }
    
    public void recusarCompra() {
    	return;
    }
    
    public void pagarAluguel() {
  	  f.pagarAluguel(this.casas);
    }
    
    public boolean verificaConstrucaoHotel() {
    	return (!f.verificaConstrucaoHotel());
    }

    // Construir casa
    public void construirCasa() {

        boolean sucesso = f.construirCasaParaJogador();

        if (sucesso) {
            System.out.println("Jogador " + f.getCorJogadorAtual() +
                               " construiu em " + f.getNomeCasaAtual());
        } else {
            System.out.println("Jogador " + f.getCorJogadorAtual() +
                               " não conseguiu construir em " + f.getNomeCasaAtual());
        }
    }
    
    // Construir hotel
    public void construirHotel() {
    	
        boolean sucesso = f.construirHotelParaJogador();

        if (sucesso) {
            System.out.println("Jogador " + f.getCorJogadorAtual() +
                               " construiu em " + f.getNomeCasaAtual());
        } else {
            System.out.println("Jogador " + f.getCorJogadorAtual() +
                               " não conseguiu construir em " + f.getNomeCasaAtual());
        }
    }

    // Pagar aluguel
//    public void pagarAluguel(CardPropriedade prop, int valorDados) {
//        Jogador j = getJogadorAtual();
//        Jogador dono = prop.getDono();
//
//        if (dono != null && dono != j && (prop.getCasas() > 0 || prop.isHotel())) {
//            int aluguel = prop.calcularAluguel(valorDados);
//            j.debito(aluguel);  
//            dono.credito(aluguel); 
//            System.out.println(j.getCor() + " pagou aluguel de " + aluguel + " a " + dono.getCor());
//        }
//    }

    // Prisão
    
    public boolean verificaPrisao() {
    	return f.jogadorIsPreso();
    }
    
    public void processarPrisao() {
        f.processarRodadaPrisao();
    }

    public void prenderJogador() {
    	System.out.println("Você foi preso!");
    	f.prenderJogador();
    }
    
    public void verificarFalencia() {
        f.verificarFalencia();
    }

    public void proximaRodada() {
        f.proximoJogador();
        if (view != null) {
            view.setSalvarEnabled(true);
        }
    }
    
    public void salvarPartida() {
        // Requisito 4ª Iteração: JFileChooser
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Salvar Jogo");
        fc.setFileFilter(new FileNameExtensionFilter("Arquivo de Texto (*.txt)", "txt"));

        int resposta = fc.showSaveDialog(view);
        if (resposta == JFileChooser.APPROVE_OPTION) {
            File arquivo = fc.getSelectedFile();
            if (!arquivo.getName().toLowerCase().endsWith(".txt")) {
                arquivo = new File(arquivo.getAbsolutePath() + ".txt");
            }
            
            // Delega para a fachada (onde seu colega vai trabalhar)
            try {
                boolean salvo = f.salvarJogo(arquivo);
                if (salvo) {
                    JOptionPane.showMessageDialog(view, "Jogo salvo com sucesso (Simulação) em:\n" + arquivo.getName());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Erro ao salvar: " + e.getMessage());
            }
        }
    }
    
    public void carregarPartidaPeloArquivo(File arquivo) {
        try {
            boolean carregado = f.carregarJogo(arquivo);
            if (carregado) {
                JOptionPane.showMessageDialog(view, "Jogo carregado com sucesso!");
                if (view != null) view.repaint();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void carregarPartida() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Carregar Jogo");
        fc.setFileFilter(new FileNameExtensionFilter("Arquivo de Texto (*.txt)", "txt"));

        int resposta = fc.showOpenDialog(view);
        if (resposta == JFileChooser.APPROVE_OPTION) {
            carregarPartidaPeloArquivo(fc.getSelectedFile());
        }
    }
    
    // Novo método para o botão "Sair" do Menu
    public void encerrarJogo() {
        String resultado = f.apurarVencedor();
        JOptionPane.showMessageDialog(view, "O jogo foi encerrado!\n" + resultado);
        System.exit(0);
    }
    
    public void incrementaRodadas() {
    	this.rodadas_jogador++;
    }
    
    public int getNumRodadas() {
    	return this.rodadas_jogador;
    }
    
    public void zeraRodadas() {
    	this.rodadas_jogador = 0;
    }
    
    // Só pra visualizar no console a quantidade de dinheiro
    public double getDinheiroJogadorAtual() {
    	return f.getDinheiroJogadorAtual();
    }
}
