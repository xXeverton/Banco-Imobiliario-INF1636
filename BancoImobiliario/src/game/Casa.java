package game;

public abstract class Casa {
    private String nome;
    
    public Casa(String nome) {
        this.nome = nome;    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    // Cada casa tem um tipo
    public abstract TipoCasa getTipo();
}
