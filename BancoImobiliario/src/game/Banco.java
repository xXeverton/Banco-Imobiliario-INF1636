package game;

import java.util.ArrayList;

class Banco {
	private int dinheiro;
	//private ArrayList<CardPropriedade> propriedades;
	//private ArrayList<CardCompanhia> companhias;
	
	public Banco() {
		this.dinheiro = 200000;
		//this.propriedades = new ArrayList<>();
		//this.companhias = new ArrayList<>();
	}

	public int getDinheiro() {
		return this.dinheiro;
	}
	
	public void premiacaoJogador(Jogador jogador) {
		jogador.credito(200);
	}
	
	public void impostoJogador(Jogador jogador) {
		jogador.debito(200);
	}
	
//	public boolean venderPropriedadeParaJogador(Jogador jogador, CardPropriedade prop) {
//        if (prop.getDono().equals("banco") && jogador.getDinheiro() >= prop.getPreco()) {
//            jogador.comprarPropriedade(prop);
//            this.dinheiro += prop.getPreco();
//            return true;
//        }
//        return false;
//    }
//    public void compraPropriedadeDoJogador(Jogador jogador, CardPropriedade prop) {
//        jogador.venderPropriedade(prop);
//    }
	
	
//	public boolean venderCompanhiaParaJogador(Jogador jogador, CardCompanhia comp) {
//	  if (comp.getDono().equals("banco") && jogador.getDinheiro() >= comp.getPreco()) {
//	      jogador.comprarCompanhia(comp);
//	      this.dinheiro += comp.getPreco();
//	      return true;
//	  }
//	  return false;
//	}
//	public void comprarCompanhiaDoJogador(Jogador jogador, CardCompanhia comp) {
//	  jogador.venderCompanhia(comp);
//	}
	
}
