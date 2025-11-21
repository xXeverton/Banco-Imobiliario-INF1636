package eventos;

public class EventoJogadorFaliu extends EventoJogo {
	
	private final int indiceJogador;
	
    public EventoJogadorFaliu(int indiceJogador) {
        super("JOGADOR_FALIU");
        this.indiceJogador = indiceJogador;
        
        put("indiceJogador", indiceJogador);
    }
    
    public int getIndiceJogador() {
    	return indiceJogador;
    }
    
    @Override
    public String toString() {
        return "EventoJogadorFaliu{indiceJogador=" + indiceJogador + "}";
    }
}