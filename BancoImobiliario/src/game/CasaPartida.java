package game;

public class CasaPartida extends Casa {
    private int bonus;

    public CasaPartida(int bonus) {
        super("Partida");
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    @Override
    public TipoCasa getTipo() {
        return TipoCasa.PARTIDA;
    }
}

