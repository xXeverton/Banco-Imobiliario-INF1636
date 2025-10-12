package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.CasaParadaLivre;
import game.TipoCasa;



public class CasaParadaLivreTest{

    private CasaParadaLivre casa;

    @Before
    public void setUp() {
        casa = new CasaParadaLivre();  
    }

    @Test
    public void testaTipo() {
    	assertEquals(TipoCasa.PARADA_LIVRE, casa.getTipo());
    }
}