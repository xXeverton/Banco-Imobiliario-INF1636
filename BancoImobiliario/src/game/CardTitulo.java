package game;

public abstract class CardTitulo extends Casa {
	private int valor;
	private Jogador dono;
	private int Idimage;
	
	public CardTitulo(String nome, int valor, int Idimage) {
		super(nome);
		this.valor = valor;
		this.dono = null;
		this.Idimage = Idimage;
	}
	
	public abstract int calcularAluguel(int dados);
	
    public boolean podeSerCompradoPor(Jogador jogador) {
        return this.getDono() == null;
    }

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Jogador getDono() {
		return this.dono;
	}

	public void setDono(Jogador dono) {
		this.dono = dono;
	}
	
	public int getIdImage() {
		return Idimage;
	}

	public void setIdImage(int idImage) {
		this.Idimage = idImage;
	}
	
    @Override
    public TipoCasa getTipo() {
        return TipoCasa.TITULO;
    }
}
