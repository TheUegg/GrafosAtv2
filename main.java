import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		File file = new File("C:\\Users\\gabri\\Downloads\\dirigido2.net");
		
		Grafo_D_NP grafo = new Grafo_D_NP();
		
		grafo.ler(file);
		
		
		while(true) {
			System.out.println("----------Menu----------");
			System.out.println("1 - Forte Conexo");
			System.out.println("2 - Ordenacao Topologica");
			int menu = reader.nextInt();
			if (menu == 1) {
				int [] fort = new int[grafo.qtdVertices()]; 
				fort = grafo.Forte_Conexo(grafo);
				break;
			}
			if (menu == 2) {
				//Ordenacao
				List<Integer> ord = new ArrayList<Integer>();
				ord = grafo.OrdenaTopo(grafo);
				for (int i = 0; i < grafo.qtdVertices()-1; i++) {
					System.out.print(grafo.rotulo(ord.get(i))+" -> ");
				}
				System.out.print(grafo.rotulo(ord.get(ord.size()-1)));
				break;
			}
		}
	}
}
