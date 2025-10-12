package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import game.Dado;
import java.util.ArrayList;

public class TesteDado {

    private Dado dado;

    @Before
    public void setUp() {
        dado = new Dado();
    }
    
    public void tearDown() {
    	dado = null;
    }

    @Test
    public void testLancarDadosRetornaDoisValores() {
        ArrayList<Integer> valores = dado.lancarDados();

        assertNotNull(valores);
        assertEquals(2, valores.size());
    }

    @Test
    public void testValoresEntreUmESeis() {
        ArrayList<Integer> valores = dado.lancarDados();

        
        int d1 = valores.get(0);
        int d2 = valores.get(1);
        
        assertTrue(d1 >= 1 && d1 <= 6);
        assertTrue(d2 >= 1 && d2 <= 6);
    }
}
