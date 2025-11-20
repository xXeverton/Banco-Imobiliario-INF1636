package game.persistencia;

import java.util.*;

public class EstadoJogo {
    public List<EstadoJogador> jogadores;
    public int indiceJogadorAtual;

    public EstadoJogo() {
        jogadores = new ArrayList<>();
    }
}