package game.persistencia;

import java.io.*;
import java.util.*;

public class SaveManager {

    // =====================
    // SALVAR PARTIDA
    // =====================
    public static void salvar(EstadoJogo estado, File arquivo) throws Exception {
        PrintWriter pw = new PrintWriter(new FileWriter(arquivo));

        pw.println("INDICE_ATUAL " + estado.indiceJogadorAtual);
        pw.println("NUM_JOGADORES " + estado.jogadores.size());

        for (EstadoJogador j : estado.jogadores) {
            pw.println("JOGADOR");

            pw.println("NUM " + j.numero);
            pw.println("COR " + j.cor);
            pw.println("DINHEIRO " + j.dinheiro);
            pw.println("POSICAO " + j.posicao);
            pw.println("PRESO " + j.preso);
            pw.println("HABEAS " + j.habeas);

            pw.println("QNT_PROPS " + j.propriedades.size());
            for (String id : j.propriedades) pw.println(id);

            pw.println("QNT_COMPANHIAS " + j.companhias.size());
            for (int id : j.companhias) pw.println(id);

            pw.println("FIM_JOGADOR");
        }

        pw.close();
    }

    // =====================
    // CARREGAR PARTIDA
    // =====================
    public static EstadoJogo carregar(File arquivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(arquivo));

        EstadoJogo estado = new EstadoJogo();
        String linha;

        linha = br.readLine();
        estado.indiceJogadorAtual = Integer.parseInt(linha.split(" ")[1]);

        linha = br.readLine();
        int numJogadores = Integer.parseInt(linha.split("\\s+")[1]);

        for (int i = 0; i < numJogadores; i++) {
            EstadoJogador ej = new EstadoJogador();

            br.readLine(); // "JOGADOR"

            ej.numero = Integer.parseInt(br.readLine().split("\\s+")[1]);
            ej.cor = br.readLine().split("\\s+")[1];
            ej.dinheiro = Double.parseDouble(br.readLine().split("\\s+")[1]);
            ej.posicao = Integer.parseInt(br.readLine().split("\\s+")[1]);
            ej.preso = Boolean.parseBoolean(br.readLine().split("\\s+")[1]);
            ej.habeas = Boolean.parseBoolean(br.readLine().split("\\s+")[1]);

            linha = br.readLine();
            String[] partes = linha.split("\\s+");
            int qntProps = Integer.parseInt(partes[1]);

            ej.propriedades = new ArrayList<>();

            for (int p = 0; p < qntProps; p++) {
                String propriedade = br.readLine();

                // garanta que não é linha vazia
                while (propriedade.trim().isEmpty()) {
                    propriedade = br.readLine();
                }

                ej.propriedades.add(propriedade.trim());
            }

            int qntComp = Integer.parseInt(br.readLine().split("\\s+")[1]);

            ej.companhias = new ArrayList<>();
            for (int c = 0; c < qntComp; c++)
                ej.companhias.add(Integer.parseInt(br.readLine().trim()));

            br.readLine(); // "FIM_JOGADOR"

            estado.jogadores.add(ej);
        }


        br.close();
        return estado;
    }
}