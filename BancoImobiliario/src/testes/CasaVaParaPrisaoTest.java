package testes;

import static org.junit.Assert.*;
import org.junit.Test;
import game.CasaVaParaPrisao;

public class CasaVaParaPrisaoTest {

    @Test
    public void testaSeArmazenaCorretamenteAPosicaoDaPrisao() {
        int posicaoDaPrisaoNoTabuleiro = 10;

        CasaVaParaPrisao casa = new CasaVaParaPrisao(posicaoDaPrisaoNoTabuleiro);

        assertEquals("A casa deveria retornar a posição da prisão que foi informada na sua criação.",
                posicaoDaPrisaoNoTabuleiro, casa.getPosicaoPrisao());
    }

    @Test
    public void testaSeArmazenaCorretamenteOutraPosicaoDaPrisao() {
        int outraPosicao = 15;

        CasaVaParaPrisao casa = new CasaVaParaPrisao(outraPosicao);

        assertEquals("A casa deveria funcionar com qualquer valor de posição da prisão.",
                outraPosicao, casa.getPosicaoPrisao());
    }
}