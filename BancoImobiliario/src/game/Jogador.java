package game;

import java.util.ArrayList;

class Jogador {
	private int numero_jogador;
	private double dinheiro;
	private ArrayList<CardPropriedade> propriedades;
	private ArrayList<CardCompanhia> companhias;
	private boolean preso;
	private String cor;
	

	public Jogador(int numero_jogador, String cor) {
		this.numero_jogador = numero_jogador;
		this.dinheiro = 4000;
		this.propriedades = new ArrayList<>();
		this.companhias = new ArrayList<>();
        this.preso = false;
        this.cor = cor;
	}
	
	public int getNumero_jogador() {
		return numero_jogador;
	}

	public void setNumero_jogador(int numero_jogador) {
		this.numero_jogador = numero_jogador;
	}

	public double getDinheiro() {
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
	        prop.setDono(this.cor);
	        return true;
    	}
    	else {
    		System.out.println("Jogador não possui dinheiro suficiente");
    		return false;
    	}
    }

    public void venderPropriedade(CardPropriedade prop) {
        if (prop.isHotel()) {
            this.dinheiro = this.dinheiro + 0.9 * (
                prop.getValor() + (prop.getPrecoCasa() * prop.getCasas()) + prop.getPrecoHotel()
            );
        } else {
            this.dinheiro = this.dinheiro + 0.9 * (
                prop.getValor() + (prop.getPrecoCasa() * prop.getCasas())
            );
        }
        this.propriedades.remove(prop);
        prop.setDono("banco");
    }
    
    public boolean comprarCompanhia(CardCompanhia comp) {
    	if (this.dinheiro > comp.getValor()) {
	    	this.dinheiro = this.dinheiro - comp.getValor();
	        this.companhias.add(comp);
	        comp.setDono(this.cor);
	        return true;
    	}
    	else {
    		System.out.println("Jogador não possui dinheiro suficiente");
    		return false;
    	}
    }

    public void venderCompanhia(CardCompanhia comp) {
    	this.dinheiro = this.dinheiro + 0.9 * comp.getValor();
        this.companhias.remove(comp);
        comp.setDono("banco");
    }

    public boolean ComprarCasa(CardPropriedade prop) {
    	if (!prop.isHotel() && this.dinheiro > prop.getPrecoHotel() && prop.getCasas() == 4) {
	    	this.dinheiro = this.dinheiro - prop.getPrecoHotel();
	        prop.setHotel(true);
	        return true;
    	}
    	else if (this.dinheiro > prop.getPrecoCasa() && prop.getCasas() < 4) {
    		prop.setCasas(prop.getCasas() + 1);
    		return true;
    	}
    	else {
    		System.out.println("Jogador não possui dinheiro suficiente");
    		return false;
    	}
    }
    
}
