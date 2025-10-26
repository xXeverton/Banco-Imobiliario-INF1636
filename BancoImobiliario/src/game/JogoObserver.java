package game;

/**
 * Interface OBRIGATÓRIA para a atualização da View,
 * seguindo o padrão Observer.
 * * Classes na camada Controller e/ou View devem implementar esta interface
 * para receber notificações da Fachada (Observable) sobre mudanças de estado.
 */
public interface JogoObserver {
    
    /**
     * Chamado pela Fachada (Observable) para notificar mudanças.
     * O método deve interpretar o objeto 'arg' para decidir qual
     * atualização gráfica ou lógica deve ser executada.
     * * @param observable A própria Fachada que disparou a notificação.
     * @param arg O objeto com o detalhe da mudança (ex: Jogador, CartaSorteReves, ArrayList<Integer> dos dados).
     */
    void update(Fachada observable, Object arg);
}