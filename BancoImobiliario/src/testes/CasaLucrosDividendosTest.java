package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import game.Banco;
import game.Jogador;

public class CasaLucrosDividendosTest {

    private final int VALOR_PREMIO = 200;
    private Jogador jogador;
    private Banco banco;

    @Before
    public void setUp() {
        jogador = new Jogador(1, "Vermelho");
        banco = new Banco();
    }


    @Test
    public void testaSeJogadorRecebeLucrosDividendos() {
        double saldoAntes = jogador.getDinheiro();

        banco.premiacaoJogador(jogador);

        double saldoEsperado = saldoAntes + VALOR_PREMIO;

        assertEquals("O saldo do jogador deveria aumentar em " + VALOR_PREMIO,
                saldoEsperado, jogador.getDinheiro(), 0.001);
    }

    @Test
    public void testaSeBancoPagaLucrosDividendos() {
        double saldoAntes = banco.getDinheiro();

        banco.premiacaoJogador(jogador);

        double saldoEsperado = saldoAntes - VALOR_PREMIO;

        assertEquals("O saldo do banco deveria diminuir em " + VALOR_PREMIO,
                saldoEsperado, banco.getDinheiro(), 0.001);
    }
}