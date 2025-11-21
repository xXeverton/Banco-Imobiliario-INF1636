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
	private boolean temHabeasCorpus;

	public Jogador(int numero_jogador, String cor) {
		this.numero_jogador = numero_jogador;
		this.dinheiro = 10;
		this.propriedades = new ArrayList<>();
		this.companhias = new ArrayList<>();
        this.preso = false;
        this.cor = cor;
        this.posicao = 0;
        this.temHabeasCorpus = false;
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
	
	public void setDinheiro(double dinheiro) {
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
            System.out.println("Não é possível construir casas, já há um hotel nesta propriedade!");
            return false;
        }

        if (this.dinheiro < propriedade.getPrecoCasa()) {
            System.out.println("Dinheiro insuficiente para construir a casa.");
            return false;
        }

        // Construindo casa
        this.dinheiro -= propriedade.getPrecoCasa();
        propriedade.setCasas(propriedade.getCasas() + 1);
        System.out.println("Casa construída em " + propriedade.getNome());
        return true;
    }

    // Constrói um hotel em uma propriedade
    public boolean construirHotel(CardPropriedade propriedade) {
        if (propriedade.isHotel()) {
            System.out.println("Já há um hotel nesta propriedade!");
            return false;
        }

        if (propriedade.getCasas() < 1) {
            System.out.println("É necessário ter pelo menos 1 casa para construir um hotel.");
            return false;
        }

        if (this.dinheiro < propriedade.getPrecoHotel()) {
            System.out.println("Dinheiro insuficiente para construir o hotel.");
            return false;
        }

        // Construindo hotel
        this.dinheiro -= propriedade.getPrecoHotel();
        propriedade.setHotel(true);
        System.out.println("Hotel construído em " + propriedade.getNome());
        return true;
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
	
	public void addHabeasCorpus() {
		this.temHabeasCorpus = true;
	}
	
	public boolean temHabeasCorpus() {
        return this.temHabeasCorpus;
    }
	
	public void usarHabeasCorpus() {
        this.temHabeasCorpus = false;
    }

	public ArrayList<CardPropriedade> getPropriedades() {
		return propriedades;
	}

	public void setPropriedades(ArrayList<CardPropriedade> propriedades) {
		this.propriedades = propriedades;
	}

	public ArrayList<CardCompanhia> getCompanhias() {
		return companhias;
	}

	public void setCompanhias(ArrayList<CardCompanhia> companhias) {
		this.companhias = companhias;
	}
	
	// Retorna os "IDs" das propriedades (nomes)
    public ArrayList<String> getIdPropriedades() {
        ArrayList<String> ids = new ArrayList<>();
        for (CardPropriedade p : propriedades) {
            ids.add(p.getNome()); // pega o nome como "ID"
        }
        return ids;
    }

    // Retorna os "IDs" das companhias (inteiros)
    public ArrayList<Integer> getIdCompanhias() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (CardCompanhia c : companhias) {
            ids.add(c.getIdImage()); // pega o ID da companhia
        }
        return ids;
    }

    // Métodos para adicionar objetos
    public void adicionarPropriedade(CardPropriedade p) {
        propriedades.add(p);
        p.setDono(this); 
    }
    
    public void adicionarCompanhia(CardCompanhia c) {
        companhias.add(c);
        c.setDono(this); 
    }
	
}
