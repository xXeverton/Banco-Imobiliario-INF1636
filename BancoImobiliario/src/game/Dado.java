package game;

import java.util.Random;
import java.util.ArrayList;

public class Dado {
	private Random random;
	
	// add
	private boolean modoTeste = false;
	private ArrayList<Integer> valoresTeste;
	
	
	public Dado() {
		this.random = new Random();
		
		// add modo testador
		this.valoresTeste = new ArrayList<>();
		this.valoresTeste.add(1);
		this.valoresTeste.add(1);
	}
	
	public ArrayList<Integer> lancarDados() {
		if (modoTeste) {
			return this.valoresTeste;
		}
        ArrayList<Integer> valores = new ArrayList<>();
        valores.add(random.nextInt(6) + 1);
        valores.add(random.nextInt(6) + 1);
        return valores;
    }
	
	public void setModoTeste(boolean modo) {
		this.modoTeste = modo;
	}
	
	public void setValoresTeste(int d1, int d2) {
		this.valoresTeste.clear();
		this.valoresTeste.add(d1);
		this.valoresTeste.add(d2);
	}
}
