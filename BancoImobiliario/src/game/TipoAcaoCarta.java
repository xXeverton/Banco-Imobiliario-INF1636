package game;

/**
 * Define os tipos de ações possíveis para as cartas Sorte ou Revés.
 * Cada tipo de ação pode ter um ID genérico associado, mas a carta específica
 * terá seu próprio idImagem (1 a 30) e valor.
 */
public enum TipoAcaoCarta {
    // Ações Positivas (Sorte)
    RECEBER_DINHEIRO_BANCO(1),        // Receber dinheiro do banco (valor varia)
    RECEBER_DINHEIRO_JOGADORES(2),    // Receber dinheiro de cada jogador (valor fixo por jogador)
    SAIDA_LIVRE_PRISAO(3),            // Carta especial "Saída Livre da Prisão"
    MOVER_PARA_PONTO_PARTIDA(4),    // Mover para o início E receber $200

    // Ações Negativas (Revés)
    PAGAR_DINHEIRO_BANCO(5),          // Pagar dinheiro ao banco (valor varia)
    IR_PARA_PRISAO(6);               // Mover para a prisão (sem receber $200)

    // === Outras ações (se houver mais tipos nas regras completas) ===
    // Ex: MOVER_X_CASAS(7),
    // Ex: IR_PARA_PROPRIEDADE_ESPECIFICA(8),
    // Ex: PAGAR_POR_CASA_HOTEL(9),

    private final int idTipoAcao; // ID do tipo de ação, não da carta específica

    TipoAcaoCarta(int idTipoAcao) {
        this.idTipoAcao = idTipoAcao;
    }

    public int getIdTipoAcao() {
        return idTipoAcao;
    }

    // Método opcional para buscar o enum pelo ID do tipo de ação, se necessário
    public static TipoAcaoCarta fromId(int id) {
        for (TipoAcaoCarta tipo : values()) {
            if (tipo.idTipoAcao == id) {
                return tipo;
            }
        }
        return null; // Ou lançar uma exceção
    }
}