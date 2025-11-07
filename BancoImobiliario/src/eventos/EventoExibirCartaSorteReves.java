package eventos;

import game.CartaSorteReves;

/**
 * Evento enviado pelo Model para a View exibir uma carta Sorte/Revés.
 */
public class EventoExibirCartaSorteReves extends EventoJogo {

    private final int idImagem;      // ID da imagem para a View exibir
    private final String descricao;  // Texto da carta
    private final String acao;       // Tipo de ação (enum convertido para String)
    private final int valor;         // Valor numérico associado à carta

    /**
     * Construtor que recebe a carta Sorte/Revés e cria o evento para a View.
     * @param carta CartaSorteReves a ser exibida.
     */
    public EventoExibirCartaSorteReves(CartaSorteReves carta) {
        super("EXIBIR_CARTA_SORTE_REVES");
        this.idImagem = carta.getIdImagem();
        this.descricao = carta.getDescricao();
        this.acao = carta.getAcao().name(); // converte enum para string para a View
        this.valor = carta.getValor();

        // Também adiciona no mapa genérico de dados da superclasse
        put("idImagem", idImagem);
        put("descricao", descricao);
        put("acao", acao);
        put("valor", valor);
    }

    // Getters

    public int getIdImagem() {
        return idImagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getAcao() {
        return acao;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "EventoExibirCartaSorteReves{" +
                "idImagem=" + idImagem +
                ", descricao='" + descricao + '\'' +
                ", acao='" + acao + '\'' +
                ", valor=" + valor +
                '}';
    }
}
