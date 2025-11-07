package game.observer;
import eventos.*; 

import java.util.ArrayList;
import java.util.List;

public abstract class Observavel {
    private List<Observador> observadores = new ArrayList<>();

    public void adicionarObservador(Observador o) {
        observadores.add(o);
    }

    public void notificarObservadores(EventoJogo evento) {
        for (Observador o : observadores) {
            o.atualizar(evento);
        }
    }
}
