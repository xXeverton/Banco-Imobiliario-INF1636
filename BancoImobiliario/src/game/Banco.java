package game;

import java.util.ArrayList;

class Banco {
	private int dinheiro;
	//private ArrayList<CardPropriedade> propriedades;
	//private ArrayList<CardCompanhia> companhias;
	//private ArrayList<CardTerrenos> terrenos;
	
	public Banco() {
		this.dinheiro = 200000;
		//this.propriedades = new ArrayList<>();
		//this.companhias = new ArrayList<>();
		//this.terrenos = new ArrayList<>();
	}

	public int getDinheiro() {
		return this.dinheiro;
	}
	
	public void premiacaoInicio(Jogador jogador) {
		jogador.credito(200);
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
	
	
//	public boolean venderTerrenoParaJogador(Jogador jogador, CardTerreno terreno) {
//	  if (terreno.getDono().equals("banco") && jogador.getDinheiro() >= terreno.getPreco()) {
//	      jogador.comprarTerreno(terreno);
//	      this.dinheiro += terreno.getPreco();
//	      return true;
//	  }
//	  return false;
//	}
//	public void comprarTerrenoDoJogador(Jogador jogador, CardTerreno terreno) {
//	  jogador.venderTerreno(terreno);
//	}
	
	
}
