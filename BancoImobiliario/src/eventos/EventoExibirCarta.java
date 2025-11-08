package eventos;

public class EventoExibirCarta extends EventoJogo {

    private final int tipoCarta;   // 1 = Título, 2 = Companhia, 3 = Sorte/Reves
    private final int idImagem;    // ID da imagem da carta
    private final String nome;     // Nome ou descrição da carta
    private final boolean compra;    // Se a casa está disponível para compra
    private final boolean dono;    // Se o dono é o jogador

    public EventoExibirCarta(int tipoCarta, int idImagem, String nome, boolean compra, boolean dono) {
        super("EXIBIR_CARTA");
        this.tipoCarta = tipoCarta;
        this.idImagem = idImagem;
        this.nome = nome;
        this.compra = compra;
        this.dono = dono;

        // Também guarda nos dados genéricos da superclasse
        put("tipoCarta", tipoCarta);
        put("idImagem", idImagem);
        put("nome", nome);
        put("compra", compra);
        put("dono", dono);
    }

    public int getTipoCarta() {
        return tipoCarta;
    }

    public int getIdImagem() {
        return idImagem;
    }

    public String getNome() {
        return nome;
    }
    
    public boolean getCompra() {
        return compra;
    }
    
    public boolean getDono() {
        return dono;
    }

    @Override
    public String toString() {
        return "EventoExibirCarta{" +
                "tipoCarta=" + tipoCarta +
                ", idImagem=" + idImagem +
                ", nome='" + nome + '\'' +
                ", compra='" + compra + '\'' +
                ", dono='" + dono + '\'' +
                '}';
    }
}
