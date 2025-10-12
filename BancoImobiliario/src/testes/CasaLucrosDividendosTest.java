package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.CasaLucrosDividendos;
import game.TipoCasa;



public class CasaLucrosDividendosTest {

    private CasaLucrosDividendos casa;

    @Before
    public void setUp() {
        casa = new CasaLucrosDividendos(200);
        
    }
    
    @Test
    public void testaValorCreditado() {
    	assertEquals(200, casa.getValor());
    }
    
    @Test
    public void testaTipo() {
    	assertEquals(TipoCasa.LUCROS_DIVIDENDOS, casa.getTipo());
    }
}