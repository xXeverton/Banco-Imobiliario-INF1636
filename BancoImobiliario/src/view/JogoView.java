package view;

import controller.JogoController;
import game.*;
import java.util.*;

public class JogoView {

    private JogoController controller;
    private Scanner scanner;
    private String[] cores = {"vermelho", "azul", "laranja", "amarelo", "roxo", "cinza"};

    public JogoView() {
        this.controller = new JogoController();
        this.scanner = new Scanner(System.in);
    }

    public void iniciarJogo() {
        System.out.println("Bem-vindo ao Banco Imobiliário!");
        System.out.print("Quantos jogadores irão jogar? (2 a 6): ");
        int numJogadores = scanner.nextInt();

        if (numJogadores < 2 || numJogadores > 6) {
            System.out.println("Número inválido de jogadores. Encerrando...");
            return;
        }

        // Define as cores
        for (int i = 0; i < numJogadores; i++) {
            String cor = cores[i];
            Jogador jogador = new Jogador(i + 1, cor);
            controller.adicionarJogador(jogador);
            System.out.println("Jogador " + jogador.getNumero_jogador() + " será " + jogador.getCor() + ".");
        }

        System.out.println("\nO jogo vai começar!");
        executarRodadas();
    }

    private void executarRodadas() {
        boolean jogoAtivo = true;
        while (jogoAtivo) {
            Jogador jogador = controller.getJogadorAtual();
            System.out.println("\nÉ a vez do jogador " + jogador.getCor());
            
            if (jogador.isPreso()) {
                System.out.println("Você está preso! Tentando sair...");
                controller.processarRodadaPrisao(jogador);
                controller.proximoJogador();
                continue;
            }

            System.out.print("Pressione ENTER para lançar os dados...");
            scanner.nextLine();
            scanner.nextLine();

            int casas = controller.lancarDados();
            controller.moverJogador(casas);

            controller.verificarFalencia();
            if (controller.getJogadores() == 1) {
            	System.out.print("Jogador " + jogador.getCor() + " ganhou!!");
            	return;
            }
            controller.proximoJogador();
        }
    }

    public static void main(String[] args) {
        new JogoView().iniciarJogo();
    }
}
