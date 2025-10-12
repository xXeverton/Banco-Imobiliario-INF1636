package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.CasaPartida;
import game.TipoCasa;



public class CasaPartidaTest {

    private CasaPartida casa;

    @Before
    public void setUp() {
        casa = new CasaPartida(200);
        
    }
    
    @Test
    public void testaValorBonus() {
    	assertEquals(200, casa.getBonus());
    }
    
    @Test
    public void testaTipo() {
    	assertEquals(TipoCasa.PARTIDA, casa.getTipo());
    }
}