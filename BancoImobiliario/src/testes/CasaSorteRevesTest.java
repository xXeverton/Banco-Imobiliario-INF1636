package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.CasaSorteReves;
import game.TipoCasa;



public class CasaSorteRevesTest{

    private CasaSorteReves casa;

    @Before
    public void setUp() {
        casa = new CasaSorteReves();  
    }

    @Test
    public void testaTipo() {
    	assertEquals(TipoCasa.SORTE_REVES, casa.getTipo());
    }
}