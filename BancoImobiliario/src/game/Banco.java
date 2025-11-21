package game;

public class Banco {
	private double dinheiro;
	
	public Banco() {
		this.dinheiro = 200000;
	}

	public double getDinheiro() {
		return this.dinheiro;
	}
	
	public void premiacaoJogador(Jogador jogador) {
		jogador.credito(200);
		this.dinheiro -= 200;
	}
	
	public void impostoJogador(Jogador jogador) {
		jogador.debito(200);
		this.dinheiro += 200;
	}
	
	public boolean venderTituloParaJogador(Jogador jogador, CardTitulo titulo) {
	    if (titulo.podeSerCompradoPor(jogador)) {
	    	if (jogador.comprarTitulo(titulo)) {
		        this.dinheiro += titulo.getValor();
		        return true;
	    	}
	    }
	    return false;
	}
	
    public void compraPropriedadeDoJogador(Jogador jogador, CardPropriedade prop) {
    	double valor;
        valor = jogador.venderPropriedade(prop);
        this.dinheiro += valor;
        
    }
	
	public void comprarCompanhiaDoJogador(Jogador jogador, CardCompanhia comp) {
	  jogador.venderCompanhia(comp);
	}
	
	public boolean construirCasaParaJogador(Jogador jogador, CardPropriedade propriedade) {
	    // Garante que o jogador seja o dono da propriedade
	    if (!propriedade.getDono().equals(jogador)) {
	        System.out.println("Jogador não é o dono da propriedade " + propriedade.getNome());
	        return false;
	    }

	    // Pede para o jogador tentar construir
	    boolean sucesso = jogador.construirCasa(propriedade);

	    // Se o jogador conseguiu construir, o banco recebe o valor
	    if (sucesso) {
	        int valorPago = (propriedade.isHotel())
	            ? propriedade.getPrecoHotel()
	            : propriedade.getPrecoCasa();
	        this.dinheiro += valorPago;
	        return true;
	    }

	    return false;
	}	
	
	public boolean construirHotelParaJogador(Jogador jogador, CardPropriedade propriedade) {
	    // Garante que o jogador seja o dono da propriedade
	    if (!propriedade.getDono().equals(jogador)) {
	        System.out.println("Jogador não é o dono da propriedade " + propriedade.getNome());
	        return false;
	    }

	    // Pede para o jogador tentar construir
	    boolean sucesso = jogador.construirHotel(propriedade);

	    // Se o jogador conseguiu construir, o banco recebe o valor
	    if (sucesso) {
	        int valorPago = (propriedade.isHotel())
	            ? propriedade.getPrecoHotel()
	            : propriedade.getPrecoCasa();
	        this.dinheiro += valorPago;
	        return true;
	    }
	    return false;
	}	
	
	public boolean pagarMultaPrisao(Jogador jogador) {
	    int multa = 50;
	    if (jogador.getDinheiro() >= multa) {
	        jogador.debito(multa);
	        this.dinheiro += multa;
	        System.out.println("Jogador " + jogador.getCor() + " pagou multa de R$" + multa + " para sair da prisão.");
	        return true;
	    } else {
	        System.out.println("Jogador " + jogador.getCor() + " não tem dinheiro suficiente para pagar a multa.");
	        return false;
	    }
	}
}
