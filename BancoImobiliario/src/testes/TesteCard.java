package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.CardPropriedade;
import game.CardCompanhia;

public class TesteCard {

    private CardPropriedade prop;
    private CardCompanhia comp;

    @Before
    public void setUp() {
        prop = new CardPropriedade("Copacabana", 1000, 200, 500, new int[]{50, 100, 150, 200, 250, 300});
        comp = new CardCompanhia("Companhia de Luz", 800, 50,8);
    }

    // --- TESTES DE CONSTRUÇÃO DE CASAS ---

    @Test
    public void testConstruirCasaInicial() {
        assertEquals(0, prop.getCasas());
        assertFalse(prop.isHotel());

        prop.setCasas(1);  
        assertEquals(1, prop.getCasas());
    }

    @Test
    public void testConstruirHotelSemCasas() {
        assertEquals(0, prop.getCasas());
        assertFalse(prop.isHotel());

        if (prop.getCasas() < 1) {
            prop.setHotel(false);
        }
        assertFalse(prop.isHotel());
    }

    @Test
    public void testConstruirHotelComCasas() {
        prop.setCasas(2);
        prop.setHotel(true); 
        assertTrue(prop.isHotel());
        assertEquals(2, prop.getCasas()); // Casas permanecem
    }

    // --- TESTES DE GETTERS E SETTERS ---

    @Test
    public void testGettersSettersPropriedade() {
        prop.setPrecoCasa(250);
        assertEquals(250, prop.getPrecoCasa());

        prop.setPrecoHotel(600);
        assertEquals(600, prop.getPrecoHotel());

        int[] novosAlugueis = {60, 120, 180, 240, 300, 360};
        prop.setAlugueis(novosAlugueis);
        assertArrayEquals(novosAlugueis, prop.getAlugueis());

        prop.setCasas(3);
        assertEquals(3, prop.getCasas());

        prop.setHotel(true);
        assertTrue(prop.isHotel());
    }
    // --- TESTES DE ALUGUEL ---

    @Test
    public void testCalcularAluguelPropriedade() {
        // Sem hotel
        prop.setCasas(0);
        assertEquals(50, prop.calcularAluguel(0));
        prop.setCasas(4);
        assertEquals(250, prop.calcularAluguel(0));

        // Com hotel
        prop.setHotel(true);
        assertEquals(300, prop.calcularAluguel(0));
    }

    @Test
    public void testCalcularAluguelCompanhia() {
        assertEquals(150, comp.calcularAluguel(3)); // 3 dados * multiplicador 50
        assertEquals(0, comp.calcularAluguel(0));   // 0 dados
    }
}
