import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws IOException {
		
		System.out.println("============================================================");
		System.out.println("Exercício 1 | grafo: dirigido2.net");
		File dirigidoFile = new File("./grafos/dirigido2.net");
		Grafo_D_NP CFC = new Grafo_D_NP();
		CFC.ler(dirigidoFile);
		int [] fort = new int[CFC.qtdVertices()]; 
		fort = CFC.Forte_Conexo(CFC);
		System.out.println("\n");
		System.out.println("============================================================");
		System.out.println("Exercício 2 | grafo: manha.net");
		File manhaFile = new File("./grafos/manha.net");
		Grafo_D_NP ordTopo = new Grafo_D_NP();
		ordTopo.ler(manhaFile);
		List<Integer> ord = new ArrayList<Integer>();
		ord = ordTopo.OrdenaTopo(ordTopo);
		for (int i = 0; i < ordTopo.qtdVertices()-1; i++) {
			System.out.print(ordTopo.rotulo(ord.get(i))+" -> ");
		}
		System.out.println(ordTopo.rotulo(ord.get(ord.size()-1)));
		System.out.println("");
		System.out.println("============================================================");
		System.out.println("Exercício 3 | grafo: agm_tiny.net");
		File AGMFile = new File("./grafos/agm_tiny.net");
		kruskal kr = new kruskal(AGMFile);
	}
}
