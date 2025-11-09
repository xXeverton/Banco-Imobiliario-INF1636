package game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


class DeckSorteReves {

    private List<CartaSorteReves> cartas;
    
    private boolean modoTeste = false;
    private int idCartaTeste = 1;


    public DeckSorteReves() {
        cartas = new LinkedList<>();
        inicializarDeck();
		embaralhar(); 
    }

    private void inicializarDeck() {
        // Cartas Sorte (IDs 1 a 15)
        cartas.add(new CartaSorteReves("A prefeitura mandou abrir uma nova avenida... Receba 25.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 25, 1)); // chance1.png
        cartas.add(new CartaSorteReves("Houve um assalto à sua loja, mas você estava segurado. Receba 150.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 150, 2)); // chance2.png
        cartas.add(new CartaSorteReves("Um amigo tinha lhe pedido um empréstimo... Receba 80.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 80, 3)); // chance3.png
        cartas.add(new CartaSorteReves("Você está com sorte. Suas ações na Bolsa... Receba 200.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 200, 4)); // chance4.png
        cartas.add(new CartaSorteReves("Você trocou seu carro usado... Receba 50.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 50, 5)); // chance5.png
        cartas.add(new CartaSorteReves("Você acaba de receber uma parcela do seu 13º salário. Receba 50.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 50, 6)); // chance6.png
        cartas.add(new CartaSorteReves("Você tirou o primeiro lugar no Torneio de Tênis... Receba 100.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 100, 7)); // chance7.png
        cartas.add(new CartaSorteReves("O seu cachorro policial tirou o 1º prêmio... Receba 100.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 100, 8)); // chance8.png
        cartas.add(new CartaSorteReves("Saída livre da prisão.", TipoAcaoCarta.SAIDA_LIVRE_PRISAO, 0, 9)); // chance9.png
        cartas.add(new CartaSorteReves("Avance até o ponto de partida e... Receba 200.", TipoAcaoCarta.MOVER_PARA_PONTO_PARTIDA, 0, 10)); // chance10.png
        cartas.add(new CartaSorteReves("Você apostou com os parceiros... Receba 50 de cada um.", TipoAcaoCarta.RECEBER_DINHEIRO_JOGADORES, 50, 11)); // chance11.png
        cartas.add(new CartaSorteReves("Você saiu de férias e se hospedou na casa de um amigo... Receba 45.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 45, 12)); // chance12.png
        cartas.add(new CartaSorteReves("Inesperadamente você recebeu uma herança... Receba 100.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 100, 13)); // chance13.png
        cartas.add(new CartaSorteReves("Você foi promovido a diretor da sua empresa. Receba 100.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 100, 14)); // chance14.png
        cartas.add(new CartaSorteReves("Você jogou na Loteria Esportiva com um grupo de amigos. Ganharam! Receba 20.", TipoAcaoCarta.RECEBER_DINHEIRO_BANCO, 20, 15)); // chance15.png

        // Cartas Revés (IDs 16 a 30)
        cartas.add(new CartaSorteReves("Um amigo pediu-lhe um empréstimo... Pague 15.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 15, 16)); // chance16.png
        cartas.add(new CartaSorteReves("Você vai casar e está comprando um apartamento novo. Pague 25.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 25, 17)); // chance17.png
        cartas.add(new CartaSorteReves("O médico lhe recomendou repouso... Pague 45.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 45, 18)); // chance18.png
        cartas.add(new CartaSorteReves("Você achou interessante assistir à estréia da temporada de ballet... Pague 30.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 30, 19)); // chance19.png
        cartas.add(new CartaSorteReves("Parabéns! Você convidou seus amigos para festejar o aniversário. Pague 100.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 100, 20)); // chance20.png
        cartas.add(new CartaSorteReves("Você é papai outra vez! Despesas de maternidade. Pague 100.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 100, 21)); // chance21.png
        cartas.add(new CartaSorteReves("Papai os livros do ano passado não servem mais... Pague 40.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 40, 22)); // chance22.png
        cartas.add(new CartaSorteReves("Vá para a prisão sem receber nada.", TipoAcaoCarta.IR_PARA_PRISAO, 0, 23)); // chance23.png
        cartas.add(new CartaSorteReves("Você estacionou seu carro em lugar proibido e entrou na contra mão. Pague 30.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 30, 24)); // chance24.png
        cartas.add(new CartaSorteReves("Você acaba de receber a comunicação do Imposto de Renda. Pague 50.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 50, 25)); // chance25.png
        cartas.add(new CartaSorteReves("Seu clube está ampliando as piscinas... Pague 25.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 25, 26)); // chance26.png
        cartas.add(new CartaSorteReves("Renove a tempo a licença do seu automóvel. Pague 30.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 30, 27)); // chance27.png
        cartas.add(new CartaSorteReves("Seus parentes do interior vieram passar umas 'férias'... Pague 45.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 45, 28)); // chance28.png
        cartas.add(new CartaSorteReves("Seus filhos já vão para a escola... Pague 50.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 50, 29)); // chance29.png
        cartas.add(new CartaSorteReves("A geada prejudicou a sua safra de café. Pague 50.", TipoAcaoCarta.PAGAR_DINHEIRO_BANCO, 50, 30)); // chance30.png    
      }

    public void embaralhar() {
        Collections.shuffle(cartas);
    }


    public CartaSorteReves tirarCarta() {
        if (cartas.isEmpty()) {
            // Poderia relançar o deck aqui se necessário
            return null;
        }
        
        if (modoTeste) {
        	for (int i = 0; i < cartas.size(); i++) {
        		if (cartas.get(i).getId() == idCartaTeste) {
        			CartaSorteReves cartaForcada = cartas.remove(i); // Tira a carta
        			return cartaForcada;
        			
        		}
        	}
        }
        
        // LinkedList permite remoção eficiente do início
        return ((LinkedList<CartaSorteReves>) cartas).removeFirst();
    }


    public void devolverCarta(CartaSorteReves carta) {
        if (carta != null) {
            // LinkedList permite adição eficiente no final
            ((LinkedList<CartaSorteReves>) cartas).addLast(carta);
        }
    }


    public int numeroDeCartas() {
        return cartas.size();
    }
    
    public void setModoTeste(boolean modo) {
        this.modoTeste = modo;
    }
    
    public void setProximaCartaTeste(int idCarta) {
        this.idCartaTeste = idCarta;
    }
}