package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.CasaVaParaPrisao;

public class CasaVaParaPrisaoTest {
	
    private CasaVaParaPrisao casa;


    @Before
    public void setUp() {
        casa = new CasaVaParaPrisao(10);  
    }
	
    @Test
    public void testaSeArmazenaCorretamenteAPosicaoDaPrisao() {
        assertEquals("A casa deveria retornar a posição da prisão que foi informada na sua criação.",
                10, casa.getPosicaoPrisao());
    }
}