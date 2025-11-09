package game;

import java.util.Random;
import java.util.ArrayList;

public class Dado {
	private Random random;
	
	public Dado() {
		this.random = new Random();
	}
	
	public ArrayList<Integer> lancarDados() {
        ArrayList<Integer> valores = new ArrayList<>();
        valores.add(random.nextInt(6) + 1);
        valores.add(random.nextInt(6) + 1);
        return valores;
    }
}
