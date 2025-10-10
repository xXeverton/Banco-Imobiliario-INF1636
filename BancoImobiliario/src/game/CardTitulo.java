package game;

abstract class CardTitulo {
	private String nome;
	private int valor;
	private String dono;
	
	public CardTitulo(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
		this.dono = "Banco";
	}
	
	public abstract int calcularAluguel(int dados);
	
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

	public String getDono() {
		return dono;
	}

	public void setDono(String dono) {
		this.dono = dono;
	}
}
