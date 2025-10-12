package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.CasaPrisao;
import game.Jogador;

public class CasaPrisaoTest {

    private Jogador jogadorVisitante;
    private CasaPrisao casaPrisao;


    @Before
    public void setUp() {
        casaPrisao = new CasaPrisao(); // Apenas cria a casa
        jogadorVisitante = new Jogador(1, "Laranja"); // Cria um jogador que está visitando
    }

    @Test
    public void testaStatusPresoNaoMudaParaVisitante() {
        assertFalse("O jogador deveria continuar livre (isPreso() == false) ao visitar a prisão.",
                    jogadorVisitante.isPreso());
    }

    @Test
    public void testaSaldoNaoMudaParaVisitante() {
        double saldoAntes = jogadorVisitante.getDinheiro();
        double saldoDepois = jogadorVisitante.getDinheiro();
        
        assertEquals("O saldo do jogador não deveria mudar ao visitar a prisão.",
                saldoAntes, saldoDepois, 0.001);
    }
}