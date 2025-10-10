package game;

public class CardPropriedade extends CardTitulo {
	private int[] alugueis;
    private int precoCasa;
    private int precoHotel;
    private int casas;
    private boolean hotel;

    public CardPropriedade(String nome, int valor, int precoCasa, int precoHotel) {
        super(nome, valor);
        this.precoCasa = precoCasa;
        this.precoHotel = precoHotel;
        this.alugueis = alugueis;
        this.casas = 0;
        this.hotel = false;
    }
    
    @Override
    public int calcularAluguel(int dados) {
        if (hotel) return alugueis[5]; 
        return alugueis[casas];       
    }
}