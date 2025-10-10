package game;

public class CasaImposto extends Casa {
    private int valorImposto;

    public CasaImposto(int valorImposto) {
        super("Imposto de Renda");
        this.valorImposto = valorImposto;
    }

    public int getValorImposto() {
        return valorImposto;
    }

    @Override
    public TipoCasa getTipo() {
        return TipoCasa.IMPOSTO;
    }
}
