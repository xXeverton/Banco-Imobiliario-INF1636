package game;

public class CasaParadaLivre extends Casa {
    public CasaParadaLivre() {
        super("Parada Livre");
    }

    @Override
    public TipoCasa getTipo() {
        return TipoCasa.PARADA_LIVRE;
    }
}

