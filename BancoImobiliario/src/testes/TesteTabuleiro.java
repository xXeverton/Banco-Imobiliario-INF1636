package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.*;

public class TesteTabuleiro {

    private Tabuleiro tabuleiro;
    private Jogador jogador;

    @Before
    public void setUp() {
        tabuleiro = new Tabuleiro();
        jogador = new Jogador(1, "Azul");
    }
    
    public void tearDown() {
    	tabuleiro = null;
    	jogador = null;
    }

    @Test
    public void testTabuleiroInicializadoCorretamente() {
        Casa primeira = tabuleiro.getCasa(0);
        assertNotNull(primeira);
    }

    @Test
    public void testGetCasaComPosicaoValida() {
        Casa casa5 = tabuleiro.getCasa(5);
        Casa casaPrisao = tabuleiro.getCasa(9);
        assertNotNull(casa5);
        assertTrue(casa5 instanceof Casa || casa5 instanceof CardTitulo);
        assertNotNull(casaPrisao);
        assertTrue(casaPrisao instanceof Casa || casaPrisao instanceof CasaPrisao);
    }

    @Test
    public void testGetCasaComPosicaoMaiorQueTamanho() {
        int tamanho = 40;
        Casa casa = tabuleiro.getCasa(tamanho + 3);
        assertNotNull(casa);
    }

    @Test
    public void testMoverJogadorAtualizaPosicao() {
        jogador.setPosicao(0);
        Casa casa = tabuleiro.moverJogador(jogador, 5);

        assertEquals(5, jogador.getPosicao());
        assertNotNull(casa);
    }

    @Test
    public void testMoverJogadorPassaPelaCasaDePartidaGanha200() {
        jogador.setPosicao(38);
        double dinheiroAntes = jogador.getDinheiro();

        tabuleiro.moverJogador(jogador, 5);
        double dinheiroDepois = jogador.getDinheiro();

        assertEquals(dinheiroAntes + 200, dinheiroDepois, 0.001);
    }
}
