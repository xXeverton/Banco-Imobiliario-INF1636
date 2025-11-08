package eventos;

public class EventoMoverJogador extends EventoJogo {

    public EventoMoverJogador(int indiceJogador, int casas) {
        super("MOVER_JOGADOR");
        put("indiceJogador", indiceJogador);
        put("casas", casas);
    }

    @Override
    public String toString() {
        return "EventoMoverJogador{indiceJogador=" + get("indiceJogador") +
                ", casas=" + get("casas") + "}";
    }
}
