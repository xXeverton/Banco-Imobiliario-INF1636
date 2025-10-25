package controller;

import game.Fachada;


import java.util.ArrayList;

public class JogoController {
    private Fachada f;

    public JogoController() {
        this.f = Fachada.getInstancia();  // Singleton
    }

    public void adicionarJogador(int numero, String cor) {
        f.adicionarJogador(numero, cor);
    }

    
    public int getJogadores() {
    	return f.getNumeroJogadores();
    }
    
    public String getCorJogadorAtual() {
    	return f.getCorJogadorAtual();
    }

    public ArrayList<Integer> lancarDados() {
    	ArrayList<Integer> valores = f.lancarDados();
    	
        System.out.println("Jogador " + f.getJogadorAtual() + " tirou " + valores.get(0) + "," + valores.get(1));
        return valores;
    }

    // Deslocar o jogador da vez
    public void moverJogador(int casas) {
        // Pede pra fachada mover o jogador e retorna o tipo da casa
        var tipo = f.moverJogador(casas); // Novo método na Fachada

        switch (tipo) {
            case PARTIDA:
                System.out.println("Caiu na casa de Partida. Recebe R$200.");
                f.premiacaoJogador();
                break;

            case PRISAO:
                System.out.println("Está apenas visitando a prisão.");
                break;

            case VA_PARA_PRISAO:
                System.out.println("Você foi preso!");
                f.prenderJogador();
                break;

            case SORTE_REVES:
                System.out.println("Comprando carta Sorte ou Revés...");
                f.comprarCartaSorteReves();
                break;

            case IMPOSTO:
                System.out.println("Pagando imposto de R$200 ao banco.");
                f.impostoJogador();
                break;

            case LUCROS_DIVIDENDOS:
                System.out.println("Recebendo dividendo de R$200 do banco.");
                f.premiacaoJogador();
                break;

            case TITULO:
            	  f.pagarAluguel(casas);
//                f.processarTituloCasaAtual();
                break;

            default:
                System.out.println("Casa livre, nada acontece.");
                break;
        }
    }

//    public void comprarTitulo(CardTitulo titulo) {
//        Jogador jogadorAtual = getJogadorAtual();
//
//        boolean sucesso = banco.venderTituloParaJogador(jogadorAtual, titulo);
//
//        if (sucesso) {
//            System.out.println("Jogador " + jogadorAtual.getCor() + 
//                               " comprou o título " + titulo.getNome());
//        } else {
//            System.out.println("Jogador " + jogadorAtual.getCor() + 
//                               " não conseguiu comprar o título " + titulo.getNome());
//        }
//    }

//    // Construir casa
//    public void construirCasa(CardPropriedade propriedade) {
//        Jogador jogadorAtual = getJogadorAtual();
//
//        boolean sucesso = banco.construirCasaParaJogador(jogadorAtual, propriedade);
//
//        if (sucesso) {
//            System.out.println("Jogador " + jogadorAtual.getCor() +
//                               " construiu em " + propriedade.getNome());
//        } else {
//            System.out.println("Jogador " + jogadorAtual.getCor() +
//                               " não conseguiu construir em " + propriedade.getNome());
//        }
//    }
    
//    // Construir hotel
//    public void construirHotel(CardPropriedade propriedade) {
//        Jogador jogadorAtual = getJogadorAtual();
//
//        boolean sucesso = banco.construirHotelParaJogador(jogadorAtual, propriedade);
//
//        if (sucesso) {
//            System.out.println("Jogador " + jogadorAtual.getCor() +
//                               " construiu em " + propriedade.getNome());
//        } else {
//            System.out.println("Jogador " + jogadorAtual.getCor() +
//                               " não conseguiu construir em " + propriedade.getNome());
//        }
//    }

    // Pagar aluguel
//    public void pagarAluguel(CardPropriedade prop, int valorDados) {
//        Jogador j = getJogadorAtual();
//        Jogador dono = prop.getDono();
//
//        if (dono != null && dono != j && (prop.getCasas() > 0 || prop.isHotel())) {
//            int aluguel = prop.calcularAluguel(valorDados);
//            j.debito(aluguel);  
//            dono.credito(aluguel); 
//            System.out.println(j.getCor() + " pagou aluguel de " + aluguel + " a " + dono.getCor());
//        }
//    }

    // Prisão
    
    public boolean verificaPrisao() {
    	return f.jogadorIsPreso();
    }
    
    public void processarPrisao() {
        f.processarRodadaPrisao();
    }

    public void verificarFalencia() {
        f.verificarFalencia();
    }

    public void proximaRodada() {
        f.proximoJogador();
    }
    
}
