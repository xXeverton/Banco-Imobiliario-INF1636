package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.CasaImposto;
import game.TipoCasa;



public class TesteCasaImposto {

    private CasaImposto casa;

    @Before
    public void setUp() {
        casa = new CasaImposto(200);
        
    }
    
    @Test
    public void testaValorCreditado() {
    	assertEquals(200, casa.getValorImposto());
    }
    
    @Test
    public void testaTipo() {
    	assertEquals(TipoCasa.IMPOSTO, casa.getTipo());
    }
}