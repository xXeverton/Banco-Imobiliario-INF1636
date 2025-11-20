package controller;

import game.Fachada;
import game.observer.*;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class JogoController {
    private Fachada f;
    private int casas;
    private int rodadas_jogador = 0;
    public JogoController() {
        this.f = Fachada.getInstancia();  // Singleton
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
    	ArrayList<Integer> valores = f.lancarDados();
    	
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
    
    public void notificarInfos(int indice) {
    	f.notificarInfos(indice);
    }
    
    // Salvamento         
        public void salvarPartida() {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Salvar partida");
            fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivo de Texto (*.txt)", "txt"));

            int resposta = fc.showSaveDialog(null);
            if (resposta == JFileChooser.APPROVE_OPTION) {
                File arquivo = fc.getSelectedFile();
                if (!arquivo.getName().endsWith(".txt")) {
                    arquivo = new File(arquivo.getAbsolutePath() + ".txt");
                }
                try {
                    // Pega o estado atual da fachada e manda salvar
                    boolean salvamento = f.salvarJogo(arquivo);
                    if (salvamento)
                    	System.out.println("Partida salva em: " + arquivo.getAbsolutePath());
                } catch (Exception e) {
                    System.out.println("Erro ao salvar: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        public void carregarPartida() {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Carregar partida");
            fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivo de Texto (*.txt)", "txt"));

            int resposta = fc.showOpenDialog(null);
            if (resposta == JFileChooser.APPROVE_OPTION) {
                File arquivo = fc.getSelectedFile();
                try {
                    boolean carregado = f.carregarJogo(arquivo);
                    if (carregado)
                    	System.out.println("Partida carregada de: " + arquivo.getAbsolutePath());
                } catch (Exception e) {
                    System.out.println("Erro ao carregar: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
