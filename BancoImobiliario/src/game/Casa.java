package game;

public abstract class Casa {
    protected String nome;

    public Casa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    // Cada casa tem um tipo
    public abstract TipoCasa getTipo();
}
