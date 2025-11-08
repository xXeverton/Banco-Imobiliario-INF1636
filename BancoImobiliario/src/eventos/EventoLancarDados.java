package eventos;

public class EventoLancarDados extends EventoJogo {

    private final int dado1;
    private final int dado2;

    public EventoLancarDados(int dado1, int dado2) {
        super("LANCAR_DADOS");
        this.dado1 = dado1;
        this.dado2 = dado2;

        // Também guarda nos dados genéricos da superclasse
        put("dado1", dado1);
        put("dado2", dado2);
    }

    public int getDado1() {
        return dado1;
    }

    public int getDado2() {
        return dado2;
    }

    @Override
    public String toString() {
        return "EventoLancarDados{dado1=" + dado1 + ", dado2=" + dado2 + "}";
    }
}
