package controller;

import game.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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

    // Deslocar o jogador da vez  // TODO Criar uma classe casa para que no tabuleiro possa andar por esse objeto
    public void moverJogador(int casas) {
        Jogador j = getJogadorAtual();
        tabuleiro.moverJogador(j, casas);
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
