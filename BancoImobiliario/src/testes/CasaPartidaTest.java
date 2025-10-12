package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import game.Banco;
import game.Jogador;

public class CasaPartidaTest {

    private final int VALOR_HONORARIO = 200; 

    private Jogador jogador;
    private Banco banco;

    @Before
    public void setUp() {
        jogador = new Jogador(1, "Preto");
        banco = new Banco();
    }

    @Test
    public void testaSeJogadorRecebeHonorario() {
        double saldoAntes = jogador.getDinheiro();

        banco.premiacaoJogador(jogador);

        double saldoEsperado = saldoAntes + VALOR_HONORARIO;


        assertEquals("O jogador deveria receber R$" + VALOR_HONORARIO + " ao passar pela Partida.",
                saldoEsperado, jogador.getDinheiro(), 0.001);
    }
    
    @Test
    public void testaSeBancoPagaHonorario() {
        // Armazena o saldo do banco antes da ação
        double saldoAntes = banco.getDinheiro();

        // Ação: O banco paga o honorário ao jogador
        banco.premiacaoJogador(jogador);

        // Resultado esperado: O saldo do banco deve diminuir em 200
        double saldoEsperado = saldoAntes - VALOR_HONORARIO;

        // Verificação
        assertEquals("O banco deveria pagar R$" + VALOR_HONORARIO + " ao jogador.",
                saldoEsperado, banco.getDinheiro(), 0.001);
    }
}