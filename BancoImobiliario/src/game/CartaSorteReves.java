// Pacote: game
package game;


public class CartaSorteReves { 

    private final String descricao;     // Texto da carta
    private final TipoAcaoCarta acao;   // Categoria da ação (do enum)
    private final int valor;            // Valor numérico (quantia, índice da casa, etc.)
    private final int idImagem;         // ID único (1 a 30) para a View buscar a imagem


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

    public int getId() {
		return this.idImagem; 
	}
}