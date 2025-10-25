// Pacote: game
package game;

/**
 * Representa uma carta individual de Sorte ou Revés.
 * Contém a descrição textual, o tipo de ação (do enum TipoAcaoCarta),
 * um valor associado à ação (quantia, índice de casa, etc.) e
 * um ID para identificar a imagem correspondente na View.
 */
public class CartaSorteReves { // Tornar pública se precisar ser acessada fora do pacote game, senão manter package-private (sem modificador)

    private final String descricao;     // Texto da carta
    private final TipoAcaoCarta acao;   // Categoria da ação (do enum)
    private final int valor;            // Valor numérico (quantia, índice da casa, etc.)
    private final int idImagem;         // ID único (1 a 30) para a View buscar a imagem

    /**
     * Construtor para criar uma nova carta Sorte ou Revés.
     * @param descricao Texto exibido na carta.
     * @param acao      O tipo de ação que a carta representa (do enum TipoAcaoCarta).
     * @param valor     Valor associado à ação (e.g., quantia em dinheiro, índice da casa).
     * @param idImagem  ID único (1-30) correspondente à imagem da carta.
     */
    public CartaSorteReves(String descricao, TipoAcaoCarta acao, int valor, int idImagem) {
        this.descricao = descricao;
        this.acao = acao;
        this.valor = valor;
        this.idImagem = idImagem;
    }

    // Getters

    public String getDescricao() {
        return descricao;
    }

    public TipoAcaoCarta getAcao() {
        return acao;
    }

    public int getValor() {
        return valor;
    }

    public int getIdImagem() {
        return idImagem;
    }

    // Opcional: Sobrescrever toString() para facilitar a depuração
    @Override
    public String toString() {
        return "CartaSorteReves{" +
               "idImagem=" + idImagem +
               ", acao=" + acao +
               ", valor=" + valor +
               ", descricao='" + descricao + '\'' +
               '}';
    }
}