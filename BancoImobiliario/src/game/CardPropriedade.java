package game;

public class CardPropriedade extends CardTitulo {
	private int[] alugueis;
    private int precoCasa;
    private int precoHotel;
    private int casas;
    private boolean hotel;
    private String id;

	public CardPropriedade(String nome, int valor, int precoCasa, int precoHotel, int[] alugueis) {
	    super(nome, valor, -1);
	    this.precoCasa = precoCasa;
	    this.precoHotel = precoHotel;
	    this.alugueis = alugueis;
	    this.casas = 0;
	    this.hotel = false;
	    this.id = nome;
	}
    
    public int[] getAlugueis() {
		return alugueis;
	}

	public void setAlugueis(int[] alugueis) {
		this.alugueis = alugueis;
	}

	public int getPrecoCasa() {
		return precoCasa;
	}

	public void setPrecoCasa(int precoCasa) {
		this.precoCasa = precoCasa;
	}

	public int getPrecoHotel() {
		return precoHotel;
	}

	public void setPrecoHotel(int precoHotel) {
		this.precoHotel = precoHotel;
	}

	public int getCasas() {
		return casas;
	}

	public void setCasas(int casas) {
		this.casas = casas;
	}

	public boolean isHotel() {
		return hotel;
	}

	public void setHotel(boolean hotel) {
		this.hotel = hotel;
	}
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
    public int calcularAluguel(int dados) {
        if (this.hotel) return alugueis[5]; 
        return alugueis[casas];       
    }
	
}