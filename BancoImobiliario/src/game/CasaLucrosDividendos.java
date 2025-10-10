package game;

public class CasaLucrosDividendos extends Casa {
    private int valor;

    public CasaLucrosDividendos(int valor) {
        super("Lucros ou Dividendos");
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public TipoCasa getTipo() {
        return TipoCasa.LUCROS_DIVIDENDOS;
    }
}
