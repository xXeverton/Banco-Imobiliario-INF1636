package game;

public abstract class CardTitulo {
	private String nome;
	private int valor;
	private Jogador dono;
	
	public CardTitulo(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
		this.dono = null;
	}
	
	public abstract int calcularAluguel(int dados);
	
    public boolean podeSerCompradoPor(Jogador jogador) {
        return this.getDono().equals(null);
    }
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
}
