package game;

public class CasaVaParaPrisao extends Casa {
    private int posicaoPrisao; // posição da prisão no tabuleiro

    public CasaVaParaPrisao(int posicaoPrisao) {
        super("Vá para a Prisão");
        this.posicaoPrisao = posicaoPrisao;
    }

    public int getPosicaoPrisao() {
        return posicaoPrisao;
    }

    @Override
    public TipoCasa getTipo() {
        return TipoCasa.VA_PARA_PRISAO;
    }
}
