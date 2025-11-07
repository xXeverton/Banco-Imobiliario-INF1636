package eventos;

public class EventoExibirCarta extends EventoJogo {

    private final int tipoCarta;   // 1 = Título, 2 = Companhia, 3 = Sorte/Reves
    private final int idImagem;    // ID da imagem da carta
    private final String nome;     // Nome ou descrição da carta

    public EventoExibirCarta(int tipoCarta, int idImagem, String nome) {
        super("EXIBIR_CARTA");
        this.tipoCarta = tipoCarta;
        this.idImagem = idImagem;
        this.nome = nome;

        // Também guarda nos dados genéricos da superclasse
        put("tipoCarta", tipoCarta);
        put("idImagem", idImagem);
        put("nome", nome);
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

    @Override
    public String toString() {
        return "EventoExibirCarta{" +
                "tipoCarta=" + tipoCarta +
                ", idImagem=" + idImagem +
                ", nome='" + nome + '\'' +
                '}';
    }
}
