package game;

import java.util.ArrayList;

public class Jogador {
	private int numero_jogador;
	private double dinheiro;
	private ArrayList<CardPropriedade> propriedades;
	private ArrayList<CardCompanhia> companhias;
	private boolean preso;
	private String cor;
	private int rodadasPreso;
	private int posicao;

	public Jogador(int numero_jogador, String cor) {
		this.numero_jogador = numero_jogador;
		this.dinheiro = 4000;
		this.propriedades = new ArrayList<>();
		this.companhias = new ArrayList<>();
        this.preso = false;
        this.cor = cor;
        this.posicao = 0;
	}
	
	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
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
	
	public boolean comprarTitulo(CardTitulo titulo) {
	    if (this.dinheiro < titulo.getValor()) {
	        System.out.println("Jogador não possui dinheiro suficiente");
	        return false;
	    }

	    this.dinheiro -= titulo.getValor();
	    titulo.setDono(this);

	    // Adiciona o título na lista correta conforme o tipo
	    if (titulo instanceof CardPropriedade propriedade) {
	        this.propriedades.add(propriedade);
	    } else if (titulo instanceof CardCompanhia companhia) {
	        this.companhias.add(companhia);
	    } else {
	        System.out.println("Tipo de título desconhecido: " + titulo.getClass().getSimpleName());
	        return false;
	    }

	    return true;
	}

    public double venderPropriedade(CardPropriedade prop) {
    	double valor = 0;
        if (prop.isHotel()) {
            valor = 0.9 * (prop.getValor() + (prop.getPrecoCasa() * prop.getCasas()) + prop.getPrecoHotel());
        } else {
        	valor = 0.9 * (prop.getValor() + (prop.getPrecoCasa() * prop.getCasas()));
        }
        this.dinheiro += valor;
        this.propriedades.remove(prop);
        prop.setDono(null); // Null = Banco
        return valor;
    }

    public void venderCompanhia(CardCompanhia comp) {
    	this.dinheiro = this.dinheiro + 0.9 * comp.getValor();
        this.companhias.remove(comp);
        comp.setDono(null);
    }

    public boolean construirCasa(CardPropriedade propriedade) {
        if (propriedade.isHotel()) {
            System.out.println("Já há um hotel nesta propriedade!");
            return false;
        }

        if (propriedade.getCasas() == 4) {
            // Tentando construir um hotel
            if (this.dinheiro >= propriedade.getPrecoHotel()) {
                this.dinheiro -= propriedade.getPrecoHotel();
                propriedade.setHotel(true);
                System.out.println("Hotel construído em " + propriedade.getNome());
                return true;
            } else {
                System.out.println("Dinheiro insuficiente para construir o hotel.");
                return false;
            }
        } else {
            // Construindo casa
            if (this.dinheiro >= propriedade.getPrecoCasa()) {
                this.dinheiro -= propriedade.getPrecoCasa();
                propriedade.setCasas(propriedade.getCasas() + 1);
                System.out.println("Casa construída em " + propriedade.getNome());
                return true;
            } else {
                System.out.println("Dinheiro insuficiente para construir a casa.");
                return false;
            }
        }
    }

	public int getRodadasPreso() {
		return this.rodadasPreso;
	}

	public void setRodadasPreso(int rodadasPreso) {
		this.rodadasPreso = rodadasPreso;
	}
	
    public void incrementarRodadaPreso() {
        if (this.preso) {
            this.rodadasPreso++;
        }
    }

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}	
}
