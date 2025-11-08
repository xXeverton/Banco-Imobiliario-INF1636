package eventos;

public class EventoMoverJogadorPrisao extends EventoJogo {

	public EventoMoverJogadorPrisao(int indiceJogador) {
	    super("MOVER_JOGADOR_PRISAO");
	    put("indiceJogador", indiceJogador);
	}

	@Override
	public String toString() {
	    return "EventoMoverJogadorPrisao{indiceJogador=" + get("indiceJogador") + "}";
	}
}
