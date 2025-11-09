package eventos;


public class EventoMoverJogadorParaPartida extends EventoJogo {

    public EventoMoverJogadorParaPartida(int indiceJogador) {
        super("MOVER_JOGADOR_PARTIDA");
        put("indiceJogador", indiceJogador); 
    }
}