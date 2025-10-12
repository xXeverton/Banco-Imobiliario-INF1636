// TesteCasaImposto.java @author <Everton Pereira Militão>

package testes;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import game.Banco;
import game.Jogador;

public class TesteCasaImposto {

    private final int IMPOSTO_COBRADO = 200;
    private Jogador jogador;
    private Banco banco;
    @Before
    public void setUp() {
        jogador = new Jogador(1, "Azul");
        banco = new Banco();
    }

    @Test
    public void testaSeJogadorPagaImposto() {
        double saldoAntes = jogador.getDinheiro();


        banco.impostoJogador(jogador);

        double saldoEsperado = saldoAntes - IMPOSTO_COBRADO;

        assertEquals("O saldo do jogador deveria diminuir em " + IMPOSTO_COBRADO,
                saldoEsperado, jogador.getDinheiro(), 0.001);
    }

    @Test
    public void testaSeBancoRecebeImposto() {
        double saldoAntes = banco.getDinheiro();

        banco.impostoJogador(jogador);

        double saldoEsperado = saldoAntes + IMPOSTO_COBRADO;

        assertEquals("O saldo do banco deveria aumentar em " + IMPOSTO_COBRADO,
                saldoEsperado, banco.getDinheiro(), 0.001);
    }
}