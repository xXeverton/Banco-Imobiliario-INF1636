package game;

import java.util.ArrayList;

public class Tabuleiro {
    private ArrayList<Casa> casas;

    public Tabuleiro() {
        casas = new ArrayList<>();
        inicializarCasas();
    }
    
    public void inicializarCasas() {
        casas.add(new CasaPartida(200));

        casas.add(new CardPropriedade("Leblon", 100, 50, 50, new int[]{6, 30, 90, 270, 400, 500}));
        casas.add(new CasaSorteReves());
        casas.add(new CardPropriedade("Av. Presidente Vargas", 60, 50, 50, new int[]{2, 10, 30, 90, 160, 250}));
        casas.add(new CardPropriedade("Av. Nossa S. de Copacabana", 60, 50, 50, new int[]{4, 20, 60, 180, 320, 450}));
        casas.add(new CardCompanhia("Companhia Ferroviária", 200, 50, 1));
        casas.add(new CardPropriedade("Av. Brigadero Faria Lima", 240, 150, 150, new int[]{20, 100, 300, 750, 925, 1100}));
        casas.add(new CardCompanhia("Companhia de Viação", 200, 50, 2));
        casas.add(new CardPropriedade("Av. Rebouças", 220, 150, 150, new int[]{18, 90, 250, 700, 875, 1050}));
        casas.add(new CardPropriedade("Av. 9 de Julho", 220, 150, 150, new int[]{22, 110, 330, 800, 975, 1150}));

        casas.add(new CasaPrisao());

        casas.add(new CardPropriedade("Av. Europa", 200, 100, 100, new int[]{16, 80, 220, 600, 800, 1000}));
        casas.add(new CasaSorteReves());
        casas.add(new CardPropriedade("Rua Augusta", 180, 100, 100, new int[]{14, 70, 200, 550, 750, 950}));
        casas.add(new CardPropriedade("Av. Pacaembú", 180, 100, 100, new int[]{14, 70, 200, 550, 750, 950}));
        casas.add(new CardCompanhia("Companhia de Táxi", 150, 40, 3));
        casas.add(new CasaSorteReves());
        casas.add(new CardPropriedade("Interlagos", 350, 200, 200, new int[]{35, 175, 500, 1100, 1300, 1500}));
        casas.add(new CasaLucrosDividendos(200));
        casas.add(new CardPropriedade("Morumbi", 400, 200, 200, new int[]{50, 200, 600, 1400, 1700, 2000}));

        casas.add(new CasaParadaLivre());

        casas.add(new CardPropriedade("Flamengo", 120, 50, 50, new int[]{8, 40, 100, 300, 450, 600}));
        casas.add(new CasaSorteReves());
        casas.add(new CardPropriedade("Botafogo", 100, 50, 50, new int[]{6, 30, 90, 270, 400, 500}));
        casas.add(new CasaImposto(200));
        casas.add(new CardCompanhia("Companhia de Navegação", 150, 40, 4));
        casas.add(new CardPropriedade("Av. Brasil", 160, 100, 100, new int[]{12, 60, 180, 500, 700, 900}));
        casas.add(new CasaSorteReves());
        casas.add(new CardPropriedade("Av. Paulista", 140, 100, 100, new int[]{10, 50, 150, 450, 625, 750}));
        casas.add(new CardPropriedade("Jardim Europa", 140, 100, 100, new int[]{10, 50, 150, 450, 625, 750}));

        casas.add(new CasaVaParaPrisao(10));

        casas.add(new CardPropriedade("Copacabana", 260, 150, 150, new int[]{22, 110, 330, 800, 975, 1150}));
        casas.add(new CardCompanhia("Companhia de Aviação", 200, 50, 5));
        casas.add(new CardPropriedade("Av. Vieira Souto", 320, 200, 200, new int[]{28, 150, 450, 1000, 1200, 1400}));
        casas.add(new CardPropriedade("Av. Atlântica", 300, 200, 200, new int[]{26, 130, 390, 900, 1100, 1275}));
        casas.add(new CardCompanhia("Companhia de Táxi Aéreo", 200, 50, 6));
        casas.add(new CardPropriedade("Ipanema", 300, 200, 200, new int[]{26, 130, 390, 900, 1100, 1275}));
        casas.add(new CasaSorteReves());
        casas.add(new CardPropriedade("Jardim Paulista", 280, 150, 150, new int[]{24, 120, 360, 850, 1025, 1200}));
        casas.add(new CardPropriedade("Brooklin", 260, 150, 150, new int[]{22, 110, 330, 800, 975, 1150}));
    }
    
    public Casa getCasa(int posicao) {
        return casas.get(posicao % casas.size());
    }
    
    public Casa moverJogador(Jogador jogador, int casasMover) {
    	int posicaoAtual =  jogador.getPosicao();
        int novaPosicao = (jogador.getPosicao() + casasMover) % casas.size();
        jogador.setPosicao(novaPosicao);
        
        // Jogador passou direto pela casa de partida
        if (posicaoAtual + casasMover > casas.size()) {
        	System.out.println("Jogador " + jogador.getNumero_jogador() + " passou pela casa de Partida e recebeu R$200!");
            jogador.credito(200);
        }
        
        Casa casa = casas.get(novaPosicao);
        return casa;
    }

}
