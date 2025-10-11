package controller;

import game.*;
import java.util.List;
import java.util.ArrayList;

public class JogoController {
    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private Dado dado;
    private int jogadorAtual;
    private Banco banco;

    public JogoController() {
        this.tabuleiro = new Tabuleiro();
        this.jogadores = new ArrayList<>();
        this.dado = new Dado();
        this.jogadorAtual = 0;
    }

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    public int lancarDados() {
    	ArrayList<Integer> valores = dado.lancarDados();
    	int resultado = valores.get(0) + valores.get(1);
        System.out.println("Jogador " + getJogadorAtual().getCor() + " tirou " + valores.get(0) + "," + valores.get(1));
        return resultado;
    }

    // Deslocar o jogador da vez
    public void moverJogador(int casas) {
        Jogador j = getJogadorAtual();
        Casa casa = tabuleiro.moverJogador(j, casas); // Retorna a casa que o jogador parou

        System.out.println("Jogador " + j.getNumero_jogador() + " caiu em " + casa.getNome());

        switch (casa.getTipo()) {
            case PARTIDA:
                System.out.println("Passou ou caiu na casa de Partida. Recebe R$200.");
                j.credito(200);
                break;

            case PRISAO:
                System.out.println("Está apenas visitando a prisão.");
                break;

            case VA_PARA_PRISAO:
                System.out.println("Você foi preso!");
                this.prenderJogador();
                break;

            case SORTE_REVES:
                System.out.println("Comprando carta Sorte ou Revés...");
//                comprarCartaSorteReves(j);
                break;

            case IMPOSTO:
                System.out.println("Pagando imposto de R$200 ao banco.");
                banco.impostoJogador(j);
                break;
                
            case LUCROS_DIVIDENDOS:
                System.out.println("Recebendo dividendo de R$200 do banco.");
                banco.premiacaoJogador(j);
                break;

            case TITULO:
            	CardTitulo prop = null;

            	if (casa instanceof CardPropriedade propriedade) {
            	    prop = propriedade;
            	} else if (casa instanceof CardCompanhia companhia) {
            	    prop = companhia;
            	}
            	 
                if (prop.getDono() == null) {
                    System.out.println("Essa propriedade está disponível para compra por R$" + prop.getValor());
                    // Aqui você pode abrir menu de compra ou fazer compra automática // TODO decidir forma de compra
                } else if (prop.getDono() != j) {
                    int aluguel = prop.calcularAluguel(casas);
                    System.out.println("Pagando aluguel de R$" + aluguel + " para Jogador " + prop.getDono().getNumero_jogador());
                    j.debito(aluguel);
                    prop.getDono().credito(aluguel);
                }
                
                break;

            default:
                System.out.println("Casa livre, nada acontece.");
                break;
        }
    }


    public void comprarTitulo(CardTitulo titulo) {
        Jogador jogadorAtual = getJogadorAtual();

        boolean sucesso = banco.venderTituloParaJogador(jogadorAtual, titulo);

        if (sucesso) {
            System.out.println("Jogador " + jogadorAtual.getCor() + 
                               " comprou o título " + titulo.getNome());
        } else {
            System.out.println("Jogador " + jogadorAtual.getCor() + 
                               " não conseguiu comprar o título " + titulo.getNome());
        }
    }

    // Construir casa
    public void construirCasa(CardPropriedade propriedade) {
        Jogador jogadorAtual = getJogadorAtual();

        boolean sucesso = banco.construirCasaParaJogador(jogadorAtual, propriedade);

        if (sucesso) {
            System.out.println("Jogador " + jogadorAtual.getCor() +
                               " construiu em " + propriedade.getNome());
        } else {
            System.out.println("Jogador " + jogadorAtual.getCor() +
                               " não conseguiu construir em " + propriedade.getNome());
        }
    }

    // Pagar aluguel
    public void pagarAluguel(CardPropriedade prop, int valorDados) {
        Jogador j = getJogadorAtual();
        Jogador dono = prop.getDono();

        if (dono != null && dono != j && (prop.getCasas() > 0 || prop.isHotel())) {
            int aluguel = prop.calcularAluguel(valorDados);
            j.debito(aluguel);  
            dono.credito(aluguel); 
            System.out.println(j.getCor() + " pagou aluguel de " + aluguel + " a " + dono.getCor());
        }
    }

    // Prisão
    public void prenderJogador() {
        getJogadorAtual().setPreso(true);
        System.out.println("Jogador " + getJogadorAtual().getCor() + " foi preso!");
    }
    
    public void processarRodadaPrisao(Jogador jogador) {
        if (jogador.isPreso()) {
            jogador.incrementarRodadaPreso();

            System.out.println("Jogador " + jogador.getCor() + 
                               " está preso há " + jogador.getRodadasPreso() + " rodada(s).");

            if (jogador.getRodadasPreso() >= 3) {
                this.soltarJogador();
            }
        }
    }

    public void soltarJogador() {
    	
        getJogadorAtual().setPreso(false);
        System.out.println("Jogador " + getJogadorAtual().getCor() + " saiu da prisão!");
    }

    public void verificarFalencia() {
        Jogador j = getJogadorAtual();
        if (j.getDinheiro() < 0) {
            System.out.println("Jogador " + j.getCor() + " faliu e saiu do jogo!");
            jogadores.remove(j);
        }
    }

    public void proximoJogador() {
        jogadorAtual = (jogadorAtual + 1) % jogadores.size();
    }

    public Jogador getJogadorAtual() {
        return jogadores.get(jogadorAtual);
    }
}
