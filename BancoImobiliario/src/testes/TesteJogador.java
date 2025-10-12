package testes;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.*;

public class TesteJogador {

    private Jogador jogador;
    private Jogador jogador2;

    private static class FakePropriedade extends CardPropriedade {
        public FakePropriedade(String nome, int valor, int precoCasa, int precoHotel, int[] alugueis) {
            super(nome, valor, precoCasa, precoHotel, alugueis);
        }
    }
  

    @Before
    public void setUp() {
        jogador = new Jogador(1, "Azul");
        jogador2 = new Jogador(2, "Amarelo");
    }

    @After
    public void tearDown() {
        jogador = null;
        jogador2 = null;
    }

    // --- TESTES BÁSICOS ---

    @Test
    public void testConstrutorInicializaCorretamente() {
        assertEquals(1, jogador.getNumero_jogador());
        assertEquals("Azul", jogador.getCor());
        assertEquals(4000, jogador.getDinheiro(), 0.001);
        assertEquals(0, jogador.getPosicao());
        assertFalse(jogador.isPreso());
    }

    @Test
    public void testCreditoEDebito() {
        jogador.credito(500);
        assertEquals(4500, jogador.getDinheiro(), 0.001);

        jogador.debito(200);
        assertEquals(4300, jogador.getDinheiro(), 0.001);
    }
    
    // --- TESTE DE ALUGUEL ---
    @Test
    public void testAluguel() {
        FakePropriedade prop = new FakePropriedade("Copacabana", 1000, 200, 500, new int[]{50, 100, 150, 200, 250, 300});
        prop.setDono(jogador);
        
        int aluguel = prop.calcularAluguel(5);
        
        jogador2.debito(aluguel);
        prop.getDono().credito(aluguel);

        assertEquals(4050, jogador.getDinheiro(), 0.001);
        assertEquals(3950, jogador2.getDinheiro(), 0.001);
    }


    // --- TESTE DE COMPRA DE PROPRIEDADE ---

    @Test
    public void testComprarTituloComDinheiroSuficiente() {
        FakePropriedade prop = new FakePropriedade("Copacabana", 1000, 200, 500, new int[]{50, 100, 150, 200, 250, 300});
        boolean resultado = jogador.comprarTitulo(prop);

        assertTrue(resultado);
        assertEquals(3000, jogador.getDinheiro(), 0.001);
        assertEquals(jogador, prop.getDono());
    }

    @Test
    public void testComprarTituloSemDinheiroSuficiente() {
        jogador.setDinheiro(500);
        FakePropriedade prop = new FakePropriedade("Leblon", 1000, 200, 500, new int[]{});
        boolean resultado = jogador.comprarTitulo(prop);

        assertFalse(resultado);
        assertNull(prop.getDono());
        assertEquals(500, jogador.getDinheiro(), 0.001);
    }

    // --- TESTE DE CONSTRUÇÃO DE CASAS ---

    @Test
    public void testConstruirCasaComDinheiroSuficiente() {
        FakePropriedade prop = new FakePropriedade("Ipanema", 1000, 200, 800, new int[]{});
        prop.setDono(jogador);

        boolean resultado = jogador.construirCasa(prop);

        assertTrue(resultado);
        assertEquals(3800, jogador.getDinheiro(), 0.001);
        assertEquals(1, prop.getCasas());
    }

    @Test
    public void testConstruirCasaSemDinheiroSuficiente() {
        jogador.setDinheiro(100);
        FakePropriedade prop = new FakePropriedade("Ipanema", 1000, 200, 800, new int[]{});
        prop.setDono(jogador);

        boolean resultado = jogador.construirCasa(prop);

        assertFalse(resultado);
        assertEquals(100, jogador.getDinheiro(), 0.001);
        assertEquals(0, prop.getCasas());
    }

    @Test
    public void testConstruirHotelQuandoTem1Casas() {
        FakePropriedade prop = new FakePropriedade("Botafogo", 1000, 200, 800, new int[]{});
        prop.setDono(jogador);
        prop.setCasas(1);

        boolean resultado = jogador.construirHotel(prop);

        assertTrue(resultado);
        assertEquals(3200, jogador.getDinheiro(), 0.001);
        assertTrue(prop.isHotel());
    }
    
    @Test
    public void testConstruirHotelQuandoNaoTemCasas() {
        FakePropriedade prop = new FakePropriedade("Botafogo", 1000, 200, 800, new int[]{});
        prop.setDono(jogador);

        boolean resultado = jogador.construirHotel(prop);

        assertFalse(resultado);
        assertEquals(4000, jogador.getDinheiro(), 0.001);
        assertFalse(prop.isHotel());
    }

    // --- TESTE DE VENDA DE PROPRIEDADE ---

    @Test
    public void testVenderPropriedadeSemHotel() {
        FakePropriedade prop = new FakePropriedade("Flamengo", 1000, 200, 800, new int[]{});
        prop.setDono(jogador);
        prop.setCasas(2);

        double recebido = jogador.venderPropriedade(prop);

        double esperado = 0.9 * (1000 + (2 * 200));
        assertEquals(esperado, recebido, 0.001);
        assertEquals(4000 + recebido, jogador.getDinheiro(), 0.001);
        assertNull(prop.getDono());
    }

    @Test
    public void testVenderPropriedadeComHotel() {
        FakePropriedade prop = new FakePropriedade("Centro", 1000, 200, 800, new int[]{});
        prop.setDono(jogador);
        prop.setCasas(4);
        prop.setHotel(true);

        double recebido = jogador.venderPropriedade(prop);

        double esperado = 0.9 * (1000 + (4 * 200) + 800);
        assertEquals(esperado, recebido, 0.001);
        assertEquals(4000 + recebido, jogador.getDinheiro(), 0.001);
        assertNull(prop.getDono());
    }

    // --- TESTES DE PRISÃO E POSIÇÃO ---

    @Test
    public void testIncrementarRodadaPreso() {
        jogador.setPreso(true);
        jogador.incrementarRodadaPreso();
        jogador.incrementarRodadaPreso();

        assertEquals(2, jogador.getRodadasPreso());
    }

    @Test
    public void testAlterarPosicao() {
        jogador.setPosicao(10);
        assertEquals(10, jogador.getPosicao());
    }
}
