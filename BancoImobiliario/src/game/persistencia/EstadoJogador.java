package game.persistencia;

import java.util.*;

public class EstadoJogador {
    public int numero;
    public String cor;
    public double dinheiro;
    public int posicao;
    public boolean preso;
    public boolean habeas;

    public List<String> propriedades;   // IDs
    public List<Integer> companhias;     // IDs
}
