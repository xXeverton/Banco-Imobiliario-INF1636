package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.CasaPrisao;
import game.TipoCasa;



public class CasaPrisaoTest{

    private CasaPrisao casa;

    @Before
    public void setUp() {
        casa = new CasaPrisao();  
    }

    @Test
    public void testaTipo() {
    	assertEquals(TipoCasa.PRISAO, casa.getTipo());
    }
}