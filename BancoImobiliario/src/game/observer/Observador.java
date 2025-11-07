package game.observer;

import eventos.EventoJogo;

public interface Observador {
	
	void atualizar(EventoJogo evento);
	
}
