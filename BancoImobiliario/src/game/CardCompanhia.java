package game;

public class CardCompanhia extends CardTitulo {
    private int multiplicador; // Ex: 50 pontos por dado

    public CardCompanhia(String nome, int valor, int multiplicador, int IdImage) {
        super(nome, valor, IdImage);
        this.multiplicador = multiplicador;
    }

    @Override
    public int calcularAluguel(int dados) {
        return dados * multiplicador;
    }
}
