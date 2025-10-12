package testes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import game.*;

public class TesteBanco {

    private Banco banco;
    private Jogador jogador;

    private static class FakePropriedade extends CardPropriedade {
        public FakePropriedade(String nome, int valor, int precoCasa, int precoHotel, int[] alugueis) {
            super(nome, valor, precoCasa, precoHotel, alugueis);
        }
    }

    private static class FakeCompanhia extends CardCompanhia {
        public FakeCompanhia(String nome, int valor, int precoUso) {
            super(nome, valor, precoUso);
        }
    }

    @Before
    public void setUp() {
        banco = new Banco();
        jogador = new Jogador(1, "Azul");
    }

    @After
    public void tearDown() {
    	banco = null;
    	jogador = null;
    }
    
    
    // --- TESTES BÁSICOS ---

    @Test
    public void testBancoIniciaComDinheiroCorreto() {
        assertEquals(200000, banco.getDinheiro(), 0.001);
    }

    @Test
    public void testPremiacaoJogador() {
        banco.premiacaoJogador(jogador);
        assertEquals(4200, jogador.getDinheiro(), 0.001);
        assertEquals(199800, banco.getDinheiro(), 0.001);
    }

    @Test
    public void testImpostoJogador() {
        banco.impostoJogador(jogador);
        assertEquals(3800, jogador.getDinheiro(), 0.001);
        assertEquals(200200, banco.getDinheiro(), 0.001);
    }

    // --- TESTE DE VENDA DE TÍTULO ---
    @Test
    public void testVenderTituloParaJogador() {
    	FakePropriedade propriedade = new FakePropriedade("Copacabana", 260, 150, 150, new int[]{22, 110, 330, 800, 975, 1150});
        boolean resultado = banco.venderTituloParaJogador(jogador, propriedade);
        assertTrue(resultado);
        assertEquals(jogador, propriedade.getDono());
        assertEquals(200260, banco.getDinheiro(), 0.001);
        assertEquals(3740, jogador.getDinheiro(), 0.001);
    }

    @Test
    public void testVenderTituloParaJogadorComDonoDaPropriedade() {
    	FakePropriedade propriedade = new FakePropriedade("Copacabana", 260, 150, 150, new int[]{22, 110, 330, 800, 975, 1150});
        jogador.setDinheiro(100);
        Jogador outro = new Jogador(2, "Vermelho");
        propriedade.setDono(outro);
        boolean resultado = banco.venderTituloParaJogador(jogador, propriedade);
        assertFalse(resultado);
        assertTrue(propriedade.getDono() == outro);
        assertEquals(200000, banco.getDinheiro(), 0.001);
    }

    // --- TESTE DE COMPRA DE PROPRIEDADE PELO BANCO ---
    @Test
    public void testCompraPropriedadeDoJogador() {
    	FakePropriedade propriedade = new FakePropriedade("Copacabana", 260, 150, 150, new int[]{22, 110, 330, 800, 975, 1150});
        propriedade.setDono(jogador);

        double dinheiroAntes = banco.getDinheiro();
        banco.compraPropriedadeDoJogador(jogador, propriedade);

        assertNull(propriedade.getDono());
        assertTrue(jogador.getDinheiro() == 4234);
        assertTrue(banco.getDinheiro() > dinheiroAntes);
    }

    @Test
    public void testComprarCompanhiaDoJogador() {
    	FakeCompanhia companhia = new FakeCompanhia("Companhia de Táxi", 150, 40);
        companhia.setDono(jogador);
        
        banco.comprarCompanhiaDoJogador(jogador, companhia);
        
        assertNull(companhia.getDono());
        assertTrue(jogador.getDinheiro() == 4135);
    }

    // --- TESTE DE CONSTRUÇÃO DE CASA E HOTEL ---
    @Test
    public void testConstruirCasaParaJogador() {
    	FakePropriedade propriedade = new FakePropriedade("Copacabana", 260, 150, 150, new int[]{22, 110, 330, 800, 975, 1150});
        propriedade.setDono(jogador);
        boolean resultado = banco.construirCasaParaJogador(jogador, propriedade);
        
        assertTrue(resultado);
        assertEquals(3850, jogador.getDinheiro(), 0.001);
        assertEquals(200150, banco.getDinheiro(), 0.001);
        assertEquals(1, propriedade.getCasas());
    }
    
    public void testConstruirCasaParaJogadorSemDinheiro() {
    	FakePropriedade propriedade = new FakePropriedade("Copacabana", 260, 150, 150, new int[]{22, 110, 330, 800, 975, 1150});
        propriedade.setDono(jogador);
        jogador.setDinheiro(100);
        boolean resultado = banco.construirCasaParaJogador(jogador, propriedade);

        assertFalse(resultado);
        assertEquals(100, jogador.getDinheiro(), 0.001);
        assertEquals(0, propriedade.getCasas());
    }

    @Test
    public void testConstruirCasaParaJogadorErrado() {
    	FakePropriedade propriedade = new FakePropriedade("Copacabana", 260, 150, 150, new int[]{22, 110, 330, 800, 975, 1150});
        Jogador outro = new Jogador(2, "Vermelho");
        propriedade.setDono(jogador);

        boolean resultado = banco.construirCasaParaJogador(outro, propriedade);

        assertFalse(resultado);
        assertEquals(4000, outro.getDinheiro(), 0.001);
        assertEquals(0, propriedade.getCasas());
    }
    
    
    @Test
    public void testConstruirHotelParaJogador() {
    	FakePropriedade propriedade = new FakePropriedade("Copacabana", 260, 150, 150, new int[]{22, 110, 330, 800, 975, 1150});
        propriedade.setDono(jogador);
        propriedade.setCasas(1);

        boolean resultado = banco.construirHotelParaJogador(jogador, propriedade);

        assertTrue(resultado);
        assertEquals(3850, jogador.getDinheiro(), 0.001);
        assertTrue(propriedade.isHotel());
        assertEquals(200150, banco.getDinheiro(), 0.001);
    }

    @Test
    public void testConstruirHotelParaJogadorErrado() {
    	FakePropriedade propriedade = new FakePropriedade("Copacabana", 260, 150, 150, new int[]{22, 110, 330, 800, 975, 1150});
        Jogador outro = new Jogador(2, "Vermelho");
        propriedade.setDono(jogador);
        propriedade.setCasas(1);
        
        boolean resultado = banco.construirHotelParaJogador(outro, propriedade);

        assertFalse(resultado);
        assertEquals(4000, outro.getDinheiro(), 0.001);
        assertEquals(1, propriedade.getCasas());
    }
    
    // --- TESTE DE MULTA ---
    @Test
    public void testPagarMultaPrisaoComDinheiroSuficiente() {
        boolean resultado = banco.pagarMultaPrisao(jogador);
        assertTrue(resultado);
        assertEquals(3950, jogador.getDinheiro(), 0.001);
        assertEquals(200050, banco.getDinheiro(), 0.001);
    }

    @Test
    public void testPagarMultaPrisaoSemDinheiro() {
        jogador.setDinheiro(40);
        boolean resultado = banco.pagarMultaPrisao(jogador);
        assertFalse(resultado);
        assertEquals(40, jogador.getDinheiro(), 0.001);
        assertEquals(200000, banco.getDinheiro(), 0.001);
    }
}
