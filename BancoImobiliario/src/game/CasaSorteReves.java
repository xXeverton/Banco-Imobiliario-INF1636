package game;

public class CasaSorteReves extends Casa {
	
    public CasaSorteReves() {
        super("Sorte ou Revés");
    }

    @Override
    public TipoCasa getTipo() {
        return TipoCasa.SORTE_REVES;
    }
}
