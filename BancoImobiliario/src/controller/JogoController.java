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
        System.out.println("Jogador " + getJogadorAtual().getNumero_jogador() + " tirou " + valores.get(0) + "," + valores.get(1));
        return resultado;
    }

    // 2️⃣ Deslocar o jogador da vez  // TODO Criar uma classe casa para que no tabuleiro possa andar por esse objeto
//    public void moverJogador(int casas) {
//        Jogador j = getJogadorAtual();
//        tabuleiro.moverJogador(j, casas);
//    }

    public void comprarPropriedade(CardTitulo titulo) {
        Jogador jogadorAtual = getJogadorAtual();

        // Verifica se é uma propriedade e se ainda não tem dono
        if (titulo instanceof CardPropriedade) {
            CardPropriedade propriedade = (CardPropriedade) titulo;

            // Pede para o banco vender a propriedade
            boolean sucesso = banco.venderPropriedadeParaJogador(jogadorAtual, propriedade);

            if (sucesso) {
                System.out.println("Jogador " + jogadorAtual.getNumero_jogador() + 
                                   " comprou a propriedade " + propriedade.getNome());
            } else {
                System.out.println("Jogador " + jogadorAtual.getNumero_jogador() + 
                                   " não conseguiu comprar a propriedade " + propriedade.getNome());
            }
        } else {
            System.out.println("Este título não é uma propriedade comprável.");
        }
    }

    // 4️⃣ Construir casa
    public void construirCasa(CardPropriedade prop) {
        Jogador j = getJogadorAtual();
        if (prop.getDono() == j && j.getDinheiro() >= prop.getPrecoCasa()) {
            prop.construirCasa();
            j.setDinheiro(j.getDinheiro() - prop.getPrecoCasa());
            System.out.println(j.getNumero_jogador() + " construiu uma casa em " + prop.getNome());
        }
    } // TODO fazer a mesma lógica de cima e criar os métodos no banco e no Jogador

    // 5️⃣ Pagar aluguel
    public void pagarAluguel(CardPropriedade prop, int valorDados) {
        Jogador j = getJogadorAtual();
        Jogador dono = prop.getDono();

        if (dono != null && dono != j && (prop.getCasas() > 0 || prop.isHotel())) {
            int aluguel = prop.calcularAluguel(valorDados);
            j.setDinheiro(j.getDinheiro() - aluguel);  // TODO método de pagar aluguel dentro de jogador
            dono.setDinheiro(dono.getDinheiro() + aluguel); // TODO método para receber aluguel dentro do jogador
            System.out.println(j.getNumero_jogador() + " pagou aluguel de " + aluguel + " a " + dono.getNumero_jogador());
        }
    }

    // 6️⃣ Prisão (simplificado)
    public void prenderJogador() {
        getJogadorAtual().setPreso(true);
        System.out.println("Jogador " + getJogadorAtual().getNumero_jogador() + " foi preso!");
    } // TODO contar rodadas que o jogador ta preso

    public void soltarJogador() {
        getJogadorAtual().setPreso(false);
        System.out.println("Jogador " + getJogadorAtual().getNumero_jogador() + " saiu da prisão!");
    }

    public void verificarFalencia() {
        Jogador j = getJogadorAtual();
        if (j.getDinheiro() < 0) {
            System.out.println("Jogador " + j.getNumero_jogador() + " faliu e saiu do jogo!");
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
