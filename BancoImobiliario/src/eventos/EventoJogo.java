package eventos;

import java.util.HashMap;
import java.util.Map;

public class EventoJogo {
    private final String tipo;               // tipo do evento, ex: "EXIBIR_CARTA", "ATUALIZAR_SALDO"
    private final Map<String, Object> dados; // dados genéricos associados ao evento

    public EventoJogo(String tipo) {
        this.tipo = tipo;
        this.dados = new HashMap<>();
    }

    public EventoJogo(String tipo, Map<String, Object> dados) {
        this.tipo = tipo;
        this.dados = dados != null ? dados : new HashMap<>();
    }

    public String getTipo() {
        return tipo;
    }

    public Map<String, Object> getDados() {
        return dados;
    }

    public Object get(String chave) {
        return dados.get(chave);
    }

    public void put(String chave, Object valor) {
        dados.put(chave, valor);
    }

    @Override
    public String toString() {
        return "EventoJogo{tipo='" + tipo + "', dados=" + dados + '}';
    }
}
