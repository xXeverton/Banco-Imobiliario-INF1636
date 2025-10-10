package game;

public class CasaPrisao extends Casa {
    public CasaPrisao() {
        super("Prisão");
    }

    @Override
    public TipoCasa getTipo() {
        return TipoCasa.PRISAO;
    }
}

