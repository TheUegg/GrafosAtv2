import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		Grafo_D_NP grafo = new Grafo_D_NP();
		
		File file = new File("C:\\Users\\gabri\\Downloads\\manha.net");
		grafo.ler(file);
		System.out.println("============================================================");
		System.out.println("Exercício 1 | grafo: dirigido2.net");
		File dirigidoFile = new File("./grafos/dirigido2.net");
		grafo.ler(dirigidoFile);
		int [] fort = new int[grafo.qtdVertices()]; 
		fort = grafo.Forte_Conexo(grafo);
		System.out.println("");
		System.out.println("============================================================");
		System.out.println("Exercício 2 | grafo: manha.net");
		File manhaFile = new File("./grafos/manha.net");
		List<Integer> ord = new ArrayList<Integer>();
		ord = grafo.OrdenaTopo(grafo);
		for (int i = 0; i < grafo.qtdVertices()-1; i++) {
			System.out.print(grafo.rotulo(ord.get(i))+" -> ");
		}
		System.out.print(grafo.rotulo(ord.get(ord.size()-1)));
		System.out.println("");
		System.out.println("============================================================");

		
	}
}
