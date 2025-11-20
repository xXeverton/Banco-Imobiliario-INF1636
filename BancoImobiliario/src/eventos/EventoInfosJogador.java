package eventos;

import java.util.Map;
import java.util.HashMap;

public class EventoInfosJogador extends EventoJogo {

    public static final String TIPO = "ATUALIZAR_INFOS_JOGADOR";

    public EventoInfosJogador(Map<String, Object> dados) {
        super(TIPO, dados);
    }

    /**
     * Método fábrica para criar um evento com todas as infos do jogador
     */
    public static EventoInfosJogador criar(
            double dinheiro,
            java.util.List<String> propriedades,
            java.util.List<Integer> companhias,
            boolean temHabeasCorpus,
            String cor
    ) {
        Map<String, Object> dados = new HashMap<>();
        dados.put("dinheiro", dinheiro);
        dados.put("propriedades", propriedades);
        dados.put("companhias", companhias);
        dados.put("temHabeasCorpus", temHabeasCorpus);
        dados.put("cor", cor);

        return new EventoInfosJogador(dados);
    }
}
