import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grafo_D_NP {

	String[] V = null;
	int[][]  E = null;
	
	public Grafo_D_NP(){
		
	}
	
	public Grafo_D_NP(String[] Vertice,int[][] E_Aresta) {
		// Array de vertices
		V = Vertice;
		// Array de Arestas, sendo N por 3, [N][0] = recebe primeiro elemento da aresta,
		// [N][1] = recebe segundo elemento da aresta, [N][2] = recebe o index desta aresta
		// [N][3] = Marcação, usado para saber o estado da aresta "fechado" ou "aberto"
		E = E_Aresta;
	}
	
	// Leitura de um arquivo e inserção do mesmo em uma lista, sendo que cada indice contém uma linha completa
		public static List<String> readFileInList(String fileName) {
		    List<String> lines = Collections.emptyList();
		    try
		    {
		      lines =
		       Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		    }
		    catch (IOException e)
		    {
		      e.printStackTrace();
		    }
		    return lines;
		}
	
	// Leitura do arquivo e inserção nos atributos do Grafo
		protected void ler(File file) throws IOException {
			// Chama o método para colocar o arquivo em uma lista
			List l = readFileInList(file.getPath());
			
			// Variavel para conta de Vertices(numero de linhas até chegar nas arestas - 1)
			int lineEdges = -1;
			
			// Coloca a primeira linha em uma string
			String strFor1 = (String) l.get(0);
			// Manipula a string para pegar o valor de Vertices
			lineEdges = Integer.parseInt(strFor1.substring(10));
			
			// Array para as Vertices
			String[] Vert = new String [lineEdges];
			
			// Calculo para verificar o numero de Arestas
			int calcAr = l.size() - lineEdges-2;
			
			
			// MultArray para as Arestas
			int[][] Arest = new int[calcAr][4];
			
			// Padrão utilizado para pegar os valores das arestas e pesos
			Pattern p = Pattern.compile("[0-9]*\\.?[0-9]+");
			// Lista que recebe os valores dos pesos e arestas
			List<Float> arestasPesosList = new ArrayList<Float>();
			
			// Percorre as linhas do arquivo que possuem as arestas e pesos
			for (int i = lineEdges+1; i < l.size(); i++) {
				// Verifica o pardrao com o texto
				Matcher m = p.matcher((CharSequence) l.get(i));
				// Onde o padrão bater com o descrito
				while (m.find()) {
					// Adiciona a lista os valores
					arestasPesosList.add(Float.parseFloat(m.group()));
				}
			}
			
			// Percorre o numero de vertices vezes
			for (int i = 0; i < lineEdges; i++) {
				// Como a lista de valores possue tanto pesos quanto arestas, utiliza lógica de PA
				// Para inserir no array de Arestas
				// Index da Aresta[i][0] - Index da arestasPesoList
				// 0-0 1-3 2-6 3-9  4-12 5-15 6-18
				Arest[i][0] = Math.round(arestasPesosList.get((i)*3));
				// Como a lista de valores possue tanto pesos quanto arestas, utiliza lógica de PA
				// Para inserir no array de Arestas
				// Index do da Aresta[i][1] - Index da arestasPesoList
				// 0-1 1-4 2-7 3-10 4-13 5-16 6-19		
				Arest[i][1] = Math.round(arestasPesosList.get(((i)*3)+1));
				// Recebe o index
				Arest[i][2] = (i);
				// str1 definida para pegar todas as Vertices
				String str1 = (String) l.get(i+1);
				// Retira '"' da string
				str1 = str1.replace("\"", "");
				// Separa a string em partes
				String[] parts = str1.split(" ");
				// Retira o numero do vertice da string
				parts[0] = "";
				// Junta todas as partes
				str1 = String.join(" ",parts);
				// Retira espaço em branco no inicio da string
				str1 = str1.substring(1);
				// Coloca o rotulo da vertice no array
				Vert[i] = str1;
			}
			
			// Percorre desde o numero de vertices até o numero de arestas
			for (int i = lineEdges; i < calcAr; i++) {
				// Como a lista de valores possue tanto pesos quanto arestas, utiliza lógica de PA
				// Para inserir no array de Arestas
				// Index da Aresta[i][0] - Index da arestasPesoList
				// 0-0 1-3 2-6 3-9  4-12 5-15 6-18
				Arest[i][0] = Math.round(arestasPesosList.get((i)*3));
				// Como a lista de valores possue tanto pesos quanto arestas, utiliza lógica de PA
				// Para inserir no array de Arestas
				// Index do da Aresta[i][1] - Index da arestasPesoList
				// 0-1 1-4 2-7 3-10 4-13 5-16 6-19		
				Arest[i][1] = Math.round(arestasPesosList.get(((i)*3)+1));
				// Recebe o index
				Arest[i][2] = (i);
			}		
			// E recebe as Arestas
			E = Arest;
			// V recebe os Vertices
			V = Vert;	
		}
		
		// Verifica a quantidade de vertices por meio do tamanho do array V
		protected int qtdVertices() {
			return V.length;
		}
		
		// Verifica a quantidade de Arestas por meio do tamanho do array E
		protected int qtdArestas() {
			return E.length;
		}
		
		
		// Verifica nas arestas quais ligações o vertice possue, e retorna o seus vizinhos
		protected List<Integer> vizinhos(int v) {
			// Lista para armazenar os vizinhos
		    List<Integer> viz = new ArrayList<Integer>();
		    // Percorre todas as arestas
		    for (int i = 0; i < E.length; i++) {
		    	// Verifica se a aresta possue o vertice do parâmetro no primeiro elemento
		    	if (E[i][0] == v) {
		    		// Adiciona o segundo elemento como vizinho
		    		viz.add(E[i][1]);
				}
		    	// Verifica se a aresta possue o vertice do parâmetro no segundo elemento
				//if(E[i][1] == v ) {
					// Adiciona o primeiro elemento como vizinho
					//viz.add(E[i][0]);
				//}
			}
		    // Retorna lista de vizinhos
			return viz;
		}

		protected int[] Forte_Conexo(Grafo_D_NP grafo) {
			
			//Criando CTFA, onde armazeno Cv,Tv,Fv,Av
			int[][] CTFA = new int[grafo.qtdVertices()][4];
			//Criando CTFA, onde armazeno Cvt,Tvt,Fvt,Avt (os criados a partir do grafo transposto)
			int[][] CTFAt = new int[grafo.qtdVertices()][4];
			
			//Chamo DFS, para preencher o CTFA
			CTFA = DFS(grafo);
			//Crio o grafo que sera o transposto
			Grafo_D_NP grafoT = grafo;
			//Chamo o metodo que retorna o grafo transposto e armazeno no grafoT
			grafoT = grafo_transp(grafoT);
			
			//Crio Cvt,Tvt,Fvt,Avt 
			boolean [] Cvt = new boolean [grafo.qtdVertices()];
			int[] Tvt = new int [grafo.qtdVertices()];
			int[] Fvt = new int [grafo.qtdVertices()];
			int[] Avt = new int [grafo.qtdVertices()];
			
			//Chamo o DFS adaptado para que o loop funcione a partir do tempo decorrido no Fv
			CTFAt = DFS_adap(grafoT,CTFA);

			//loop que armazena o Avt
			for (int i = 0; i < grafo.qtdVertices(); i++) {		
				Avt[i] = CTFAt[i][3];
			}
			
			//Lista utilizada para armazenar os elementos fortemente conexos
			List<Integer> final2 = new ArrayList<Integer>();
			
			//Loop para colocar aos pares ligados dos elementos fortemente conexos
			for (int i = 0; i < grafo.qtdVertices(); i++) {		
				if(Avt[i] != 0) {
					final2.add(i+1);
					final2.add(Avt[i]);
				}
			}
			
			//Método usado para remover os elementos repetidos da lista
			final2 = removeDuplicatas((ArrayList<Integer>) final2);
			
			//Lista utilizada para print
			int[][] final3 = new int[grafo.qtdVertices()][2];	
			
			//Caso tenha algum elemento fortemente conexo
			if(final2.size() > 0) {
				//Adiciono final2 e Avt para a lista de print, no qual consigo fazer a separacao dos conjuntos
				for (int i = 0; i < grafo.qtdVertices(); i++) {
					final3[i][0] = final2.get(i);
					final3[i][1] = Avt[i];
				}
				//for e ifs para organizar o print
				for (int i = 0; i < final3.length-1; i++) {
					if(final3[i][1] != 0 && final3[i+1][1] != 0) {
						System.out.print(final3[i][0]+",");
					}
					if(final3[i][1] != 0 && final3[i+1][1] == 0) {
						System.out.print(final3[i][0]);
					}
					if(final3[i][1] == 0 && i != 0) {
						System.out.print("] ["+final3[i][0]+",");	
					}
					if(i == 0) {
						System.out.print("[");					
					}
				}
				System.out.print(final3[final3.length-1][0]+"]");
			//Quando não possui nenhum elemento fortemente conexo com ligacao a outro
			}else {
				//Print todos os elementos separadamente
				for (int i = 0; i < grafo.qtdVertices(); i++) {
					System.out.print("["+(i+1)+"]");
				}
			}

			//retorno a lista Avt
			return Avt;
		}
		
		protected int[][] DFS_adap(Grafo_D_NP grafo, int[][] CTFA) {	
			//Criar Cv, Tv, Fv, Av
			int [] Cv = new int [grafo.qtdVertices()];
			int[] Tv = new int [grafo.qtdVertices()];
			int[] Fv = new int [grafo.qtdVertices()];
			int[] Av = new int [grafo.qtdVertices()];
			
			//Inicializlo Cv,Tv,Fv,Av
			for (int i = 0; i < grafo.qtdVertices(); i++) {
				Cv[i] = 0;
				Tv[i] = (int) Double.POSITIVE_INFINITY;
				Fv[i] = (int) Double.POSITIVE_INFINITY;
				Av[i] = 0;
			}
			//Inicializo o tempo, utilizado para Tv e Fv
			int tempo = 0;
			//Array criado para auxiliar no loop de Fv
			int[] index = new int[grafo.qtdVertices()];
			
			//Variaveis auxiliares em relacao ao array index
			int count = 0;
			int largest = 0;
			//Loop para ordenar os indexes do maior para o menor(esse é o conteudo contido no array index)
			for (int j = 0; j < grafo.qtdVertices(); j++) {
				for ( int i = 0; i < grafo.qtdVertices(); i++ ) {
					//Comparação para armazenar o maior valor no array
					if ( CTFA[i][2] > CTFA[largest][2]) {
						largest = i;
					}
				}
				//Armazenar index referente ao maior valor de Fv no index
				index[count] = largest;
				count++;
				//Retirar o valor de Fv, para que possa continuar fazendo as comparacoes
				CTFA[largest][2] = 0;
			}
			
			//loop adaptado funcionando a partir de Fv
			for (int i : index) {
				//Testa para ver se o elemento analizado foi visitado anteriormente
				if (Cv[i] == 0){
					//Chama o metodo DFS_Visit, no qual fara as atribuicoes
					DFS_Visit(grafo,i,Cv,Tv,Fv,Av, tempo);
				}
			}
			
			//Criacao de CTFAt, para armazenar todos os dados e os retorna-los posteriormente
			int[][] CTFAt = new int[grafo.qtdVertices()][4];
			//Armazenar os dados
			for (int i = 0; i < grafo.qtdVertices(); i++) {
				CTFAt[i][0] = Cv[i];
				CTFAt[i][1] = Tv[i];
				CTFAt[i][2] = Fv[i];
				CTFAt[i][3] = Av[i];
			}
			//Retorna todos os dados obtidos	
			return CTFAt;
		}
		
		protected Grafo_D_NP grafo_transp(Grafo_D_NP grafoT) {
			//Loop que modifica o sentido das arestas
			for (int i = 0; i < E.length; i++) {
				int temporario = grafoT.E[i][0];
				grafoT.E[i][0] = grafoT.E[i][1];
				grafoT.E[i][1] = temporario;
			}	
			//Retorna o grafico transposto
			return grafoT;
		}
		
		protected int[][] DFS(Grafo_D_NP grafo) {
			
			//Criar Cv, Tv, Fv, Av
			int [] Cv = new int [grafo.qtdVertices()];
			int[] Tv = new int [grafo.qtdVertices()];
			int[] Fv = new int [grafo.qtdVertices()];
			int[] Av = new int [grafo.qtdVertices()];
			//Inicializa Cv,Tv,Fv,Av
			for (int i = 0; i < grafo.qtdVertices(); i++) {
				Cv[i] = 0;
				Tv[i] = (int) Double.POSITIVE_INFINITY;
				Fv[i] = (int) Double.POSITIVE_INFINITY;
				Av[i] = 0;
			}
			//Inicializa variavel tempo, utilizada em Tv e Fv
			int tempo = 0;
			
			//loop para fazer as atribuicoes iniciais
			for (int i = 0; i < grafo.qtdVertices(); i++) {
				//Testa se o elemento observado foi visitado anteriormente
				if (Cv[i] == 0){
					//Chama o metodo DFS_Visit, onde fara as atribuicoes
					tempo = DFS_Visit(grafo,i,Cv,Tv,Fv,Av, tempo);
				}
			}
			
			//Cria CTFA, para armazenar os dados obtidos referente ao metodo anterior
			int[][] CTFA = new int[grafo.qtdVertices()][4];
			//Armazena os dados
			for (int i = 0; i < grafo.qtdVertices(); i++) {
				CTFA[i][0] = Cv[i];
				CTFA[i][1] = Tv[i];
				CTFA[i][2] = Fv[i];
				CTFA[i][3] = Av[i];
			}
			//Retorna a matriz com Cv,Tv,Fv,Av
			return CTFA;
		}
		
		protected int DFS_Visit(Grafo_D_NP grafo,int v,int [] Cv,int[] Tv,int[] Fv,int[] Av, int tempo) {
			//Atribui o elemento a marcacao de visitado
			Cv[v] = 1;
			//Aumenta o tempo
			tempo++;
			//Insere o tempo atual em Tv(Tempo de inicio)
			Tv[v] = tempo;
			//loop que observa os visinhos do elemento vizitado
			for (int u : grafo.vizinhos(v+1)) {
				//Testa se o elemento observado foi visitado anteriormente
				if (Cv[u-1] == 0) {
					//Insere o valor V para o Antecessor
					Av[u-1] = v+1;
					//Utiliza recursao para continuar a busca no grafo
					tempo = DFS_Visit(grafo,u-1,Cv,Tv,Fv,Av, tempo);
				}
			}
			//Aumenta o tempo
			tempo++;
			//Armazena o tempo atual em Fv(tempo de fim)
			Fv[v] = tempo;
			//Retorna o tempo
			return tempo;
		}
		
		protected List<Integer> OrdenaTopo(Grafo_D_NP grafo) {
			
			// Criando CV, Tv, Fv para poder armazenar os valores
			boolean [] Cv = new boolean [grafo.qtdVertices()];
			double[] Tv = new double [grafo.qtdVertices()];
			double[] Fv = new double [grafo.qtdVertices()];
			
			//Inicializando os valores de Cv, Tv, Fv
			for (int i = 0; i < grafo.qtdVertices(); i++) {
				Cv[i] = false;
				Tv[i] = Double.POSITIVE_INFINITY;
				Fv[i] = Double.POSITIVE_INFINITY;
			}
			//Inicializando a contagem de tempo
			int tempo = 0;
			
			//Criando lista para a Ordenacao
			List<Integer> Ordena = new ArrayList<Integer>();
			
			//Loop para a quantidade de vertices do grafo
			for (int i = 0; i < grafo.qtdVertices(); i++) {
				//Testa se o elemento ja foi visitado
				if (Cv[i] == false){
					//Chama o método DFS_Visit_Ord, quando o elemento n foi visitado
					DFS_Visit_Ord(grafo,i,Cv,Tv,Fv, tempo,Ordena);
				}
			}
			//Retorna a lista ordenada
			return Ordena;
		}
		
		
		public void DFS_Visit_Ord(Grafo_D_NP grafo,int v,boolean [] Cv,double[] Tv,double[] Fv, int tempo,List Ordena) {
			//Defini que o elemento foi visitado
			Cv[v] = true;
			//Aumenta o tempo
			tempo++;
			//Define o tempo de inicio
			Tv[v] = tempo;
			//Loop que olha pelos vizinhos do elemento observado
			for (int u : grafo.vizinhos(v+1)) {	
				//Testa se o elemento foi visitado
				if (Cv[u-1] == false) {
					//Chama recursivamente o metodo, caso o elento vizinho nao tenha sido visitado
					DFS_Visit_Ord(grafo,u-1,Cv,Tv,Fv, tempo, Ordena);
				}
			}
			//Aumenta o tempo
			tempo++;
			//Define o tempo final
			Fv[v] = tempo;
			//Adiciona o elemento ao inicio do lista de ordenacao
			Ordena.add(0,v);
		}
		
		// Retorna o conteudo do Array de Vertices(Rótulo)
		protected String rotulo(int index) {
			return V[index];
		}
		
		public static <Integer> ArrayList<Integer> removeDuplicatas(ArrayList<Integer> list) {
	  
	        //Cria lista Hash
	        Set<Integer> set = new LinkedHashSet<>();
	        //Adiciona os elementos
	        set.addAll(list);
	        //Limpa a lista
	        list.clear();
	        //Adiciona sem dupliucatas
	        list.addAll(set);
	        //Returna a lista
	        return list;
	    }
		
}