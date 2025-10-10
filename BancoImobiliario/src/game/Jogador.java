package game;

import java.util.ArrayList;

class Jogador {
	private int numero_jogador;
	private int dinheiro;
	private ArrayList<CardPropriedade> propriedades;
	private boolean preso;
	

	public Jogador(int numero_jogador){
		this.numero_jogador = numero_jogador;
		this.dinheiro = 4000;
		this.propriedades = new ArrayList<>();
        this.preso = false;
	}

	
	public int getNumero_jogador() {
		return numero_jogador;
	}

	public void setNumero_jogador(int numero_jogador) {
		this.numero_jogador = numero_jogador;
	}

	public int getDinheiro() {
		return dinheiro;
	}
	
	public void setDinheiro(int dinheiro) {
		this.dinheiro = dinheiro;
	}

	public boolean isPreso() {
		return preso;
	}

	public void setPreso(boolean preso) {
		this.preso = preso;
	}
	
	public void debito(int valor) {
		this.dinheiro = this.dinheiro - valor;
	}
	
	public void credito(int valor) {
		this.dinheiro = this.dinheiro + valor;
	}
	
    public boolean comprarPropriedade(CardPropriedade prop) {
    	if (this.dinheiro > prop.getValor()) {
	    	this.dinheiro = this.dinheiro - prop.getValor();
	        this.propriedades.add(prop);
	        return true;
    	}
    	else {
    		System.out.println("Jogador não possui dinheiro suficiente");
    		return false;
    		
    	}
    }

    public void venderPropriedade(CardPropriedade prop) {
    	this.dinheiro = 0.9*(prop.value + (prop.casa.value * prop.num_casas) + (prop.hotel.value * prop.num_hoteis));
    	prop.setDono("banco");
        this.propriedades.remove(prop);
    }
}
