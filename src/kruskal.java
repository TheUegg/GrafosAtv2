import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class kruskal extends Grafo_ND_P {
	protected List<List<Integer>> A = new ArrayList<List<Integer>>();
	
	public kruskal(Grafo_ND_P grafo) {
		super(grafo.V, grafo.E, grafo.w);
		executarKruskal();
	}
	
	public kruskal(File fileGrafo) throws IOException {
		super(fileGrafo);
		executarKruskal();
	}
	
	protected void executarKruskal() {
		// Lista de Listas de cada vértice
		List<List<Integer>> S = new ArrayList<List<Integer>>();
		// Alimentando S com seus proprios vértices
		for (int i=0; i < super.qtdVertices(); i++) {
			List<Integer> Si = new ArrayList<Integer>();
			Si.add(i+1);
			S.add(Si); // Adicionando os indexes dos vértices em ordem
		}
		// Lista de vértices em ordem crescente
		int [][] Elinha = super.arestasCrescentes();
		for (int i=0; i < super.qtdArestas(); i++) {
			// Definição de Su e Sv para facilitar a leitura do código
			List<Integer> Su = S.get(Elinha[i][0] -1);
			List<Integer> Sv = S.get(Elinha[i][1] -1);
			// se Su != Sv
			// Essa comparação foi feita utilizando teoria de conjunto
			// Eles são diferentes sse a intersecção entre eles for vazia
			// ou a diferença entre eles NÃO for vazia
			HashSet<Integer> intersec = new HashSet<>();
		    intersec.addAll(Su);
		    intersec.retainAll(Sv);
		    HashSet<Integer> dif = new HashSet<>();
		    dif.addAll(Su);
		    dif.removeAll(intersec);
		    if (intersec.isEmpty() || !(dif.isEmpty())) {
		    	//System.out.println("in\n\n");
		    	// A recebe {u, v}
		    	List<Integer> uv = new ArrayList<Integer>();
		    	uv.add(Elinha[i][0]);
		    	uv.add(Elinha[i][1]);
		    	// Adicionando {u, v} à A
		    	A.add(uv);
		    	// Definindo x como a união dos conjuntos Su e Sv
		    	List<Integer> x = new ArrayList<Integer>();
		    	HashSet<Integer> union = new HashSet<>();
		    	union.addAll(Su);
		    	union.removeAll(intersec);
		    	union.addAll(Sv);
		    	x.addAll(union);
		    	// Preenchendo os conjuntos dos vértices envolvidos
		    	for (int y=0; y<x.size(); y++) {
		    		List<Integer> Sy = completaSy(S.get(x.get(y)-1), x);
		    		S.set(x.get(y)-1, Sy);
		    	}
		    }
		}
		printKruskal();
	}
	
	protected List<Integer> completaSy(List<Integer> Sy, List<Integer> x) {
		// Aqui pega-se a intersecção de x e Sy e se faz Sy - intersecção
		// e se adiciona o x, para se obter uma lista com todos os valores
		// sem repetições
		List<Integer> intersec = new ArrayList<Integer>();
		intersec.addAll(Sy);
		intersec.retainAll(x);
		List<Integer> union = new ArrayList<Integer>();
		union.addAll(Sy);
		union.removeAll(intersec);
		union.addAll(x);
		return union;
	}
	
	protected void printKruskal() {
		float cost = 0;
		for (int i=0; i<A.size(); i++) {
			cost = cost + super.peso(A.get(i).get(0), A.get(i).get(1));
		}
		System.out.println(cost);
		for (int i=0; i<A.size(); i++) {
			System.out.print(A.get(i).get(0) + "-" + A.get(i).get(1));
			if (i != A.size()-1) {
				System.out.print(", ");
			}
		}
		System.out.println();
	}
}
