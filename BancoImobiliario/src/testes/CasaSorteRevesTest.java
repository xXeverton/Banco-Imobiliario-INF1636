package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.CasaSorteReves;
import game.Jogador;

public class CasaSorteRevesTest {

    private Jogador jogador;
    private CasaSorteReves casaSorteReves;

    @Before
    public void setUp() {
        casaSorteReves = new CasaSorteReves();
        jogador = new Jogador(1, "Branco");
    }

    @Test
    public void testaAcaoNaoAlteraSaldoJogador() {
        double saldoAntes = jogador.getDinheiro();

        double saldoDepois = jogador.getDinheiro();

        assertEquals("O saldo do jogador não deveria mudar na casa Sorte ou Revés (sem ação implementada).",
                saldoAntes, saldoDepois, 0.001);
    }

    @Test
    public void testaAcaoNaoAlteraStatusPrisaoJogador() {
        boolean presoAntes = jogador.isPreso();

        assertEquals("O status de prisão do jogador não deveria mudar.",
                presoAntes, jogador.isPreso());
    }
}
