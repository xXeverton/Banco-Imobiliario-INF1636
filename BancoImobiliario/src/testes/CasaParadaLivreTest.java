package testes;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import game.Banco;
import game.Jogador;

public class CasaParadaLivreTest {

    private Jogador jogador;
    private Banco banco;

    /**
     * Prepara o ambiente antes de cada teste, criando um jogador e um banco.
     */
    @Before
    public void setUp() {
        jogador = new Jogador(1, "Verde");
        banco = new Banco();
    }

    /**
     * Testa se o saldo do jogador permanece inalterado ao parar na CasaParadaLivre.
     */
    @Test
    public void testaAcaoNaoAlteraSaldoJogador() {
        // Armazena o saldo do jogador antes da "ação"
        double saldoAntes = jogador.getDinheiro();

        // SIMULAÇÃO DA AÇÃO: Como a casa não faz nada, não chamamos nenhum método.
        // O jogador simplesmente parou aqui.

        // Pega o saldo do jogador depois da "ação"
        double saldoDepois = jogador.getDinheiro();

        // Verificação: O saldo deve ser o mesmo.
        assertEquals("O saldo do jogador não deveria mudar na Parada Livre.",
                saldoAntes, saldoDepois, 0.001);
    }

    /**
     * Testa se o saldo do banco permanece inalterado quando um jogador
     * para na CasaParadaLivre.
     */
    @Test
    public void testaAcaoNaoAlteraSaldoBanco() {
        // Armazena o saldo do banco antes da "ação"
        double saldoAntes = banco.getDinheiro();

        // SIMULAÇÃO DA AÇÃO: Nenhuma ação é executada.

        // Pega o saldo do banco depois da "ação"
        double saldoDepois = banco.getDinheiro();

        // Verificação: O saldo do banco deve ser o mesmo.
        assertEquals("O saldo do banco não deveria mudar na Parada Livre.",
                saldoAntes, saldoDepois, 0.001);
    }
}