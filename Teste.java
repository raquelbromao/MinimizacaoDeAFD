package minimizacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Teste {
	//	INICIO VARIAVEIS GLOBAIS
	//	Estado Inicial
	static Estado q0 = new Estado("q0",false,true,null);
	//	Estados nem iniciais e nem finais
	static Estado q1 = new Estado("q1",false,false,null);
	static Estado q2 = new Estado("q2",false,false,null);
	static Estado q4 = new Estado("q4",false,false,null);
	//	Estado Final
	static Estado q3 = new Estado("q3",true,false,null);	
	//	Estrutura para armazenar todos os estados do AFD
	static ArrayList<Estado> estados = new ArrayList<Estado>();
	// Estrutura para armazenar todos os pares (D(i,j)) da Tabela de minimizacao
	static ArrayList<Par> paresD = new ArrayList<Par>();
	//	Estrutura que armazena o alfabeto do AFD
	static ArrayList<Character> alfabeto = new ArrayList<Character>();
	//	Estrutura para armazenar todos os estados finais do AFD
	static ArrayList<Estado> estadosFinais = new ArrayList<Estado>();
	//	FIM VARIAVEIS GLOBAIS
	
	/**
	 * Funcao que adiciona aos pares do AFD os pares que dependem deles para serem iguais
	 * @param e1 Objeto Estado do Par
	 * @param e2 Objeto Estado do Par
	 * @param dependente Objeto Par que depende do Par(e1,e2) para ser igual
	 */
	public static void addParDependente(Estado e1, Estado e2, Par dependente) {
		//	Verifica se esse par existe (par formado pelo estado x1 e x2)
		//	Se existir adiciona o Par dependente a estrutura paresDependentes
		for (int i = 0; i < paresD.size(); i++) {
			//	Verifica existência do par formado por x1 e x2 -> Par(x1,x2) OU Par(x2,x1)
			if ((paresD.get(i).par1.equals(e1) && paresD.get(i).par2.equals(e2)) || 
				(paresD.get(i).par1.equals(e2) && paresD.get(i).par2.equals(e1))) {
				System.out.println("Par formado por ["+e1.getNome()+", "+e2.getNome()+"] existe! Adicionar dependencia!");
				paresD.get(i).paresDependentes.add(dependente);
			} else {
				System.out.println("ERRO: Este par {"+e1.getNome()+", "+e2.getNome()+"} nao existe! VERIFICAR!");
			}
		}
	}

	/**
	 * Funcao que analisa a igualdade de transicoes entre os pares
	 * @param parT par a ser analisado
	 * @param alfabeto estrutura que guarda todos os simbolos do alfabeto para analise
	 */
	static public void verificaPar(Par parT, ArrayList<Character> alfabeto) {
		Estado d1 = new Estado(null, false, false, null);
		Estado d2 = new Estado(null, false, false, null);
		
		//	Verifica se o par não possui transicoes vazia
		if (parT.par1.transicoes.isEmpty() || parT.par2.transicoes.isEmpty()) {
			System.out.println("ERRO: Um dos estados tem o mapa de transicao nulo! VERIFICAR!");
		} else {
			// Verifica se o par tem transicoes completas
			for (int i = 0; i < alfabeto.size(); i++) {
				char aux = alfabeto.get(i);
				if ((parT.par1.transicoes.containsKey(alfabeto.get(i))) == true && (parT.par2.transicoes.containsKey(alfabeto.get(i))) == true) {
					//	Verificado que o par é não-nulo e completo se verifica se são iguais para todo o alfabeto
					//System.out.println("TUDO CERTO ATE AQUI! YEY!");
					//	Se tudo estiver ok, se inicia a verificacao da igualdade de transicoes para estados iguais
					//	Verifica para transicoes de i (se &(qi,i) = &(qj,i), entao o par pode ser igual)
					//	Armazena o nomes dos estados dos que cada estado do par faz uma transição com i
					String aux1 = parT.par1.transicoes.get(aux);
					String aux2 = parT.par2.transicoes.get(aux);
					//	Verifica se os dois estados destinos sao finais ou nao-finais
					//	Se um for final e o outro nao-final, o par não pode ser igual e o motivo e armazenado 
					for (int j = 0; j < estados.size(); j++) {
						//System.out.println("Analise de igualdade do par ("+parT.par1.getNome()+", "+parT.par2.getNome()+") na transicao com "+aux+":");
						//	Armazena estado destino do Par1
						if (estados.get(j).nome.equals(aux1)) {
							d1 = estados.get(j);							
						}//	Armazena destino do Par2
						if (estados.get(j).nome.equals(aux2)) {
							d2 = estados.get(j);
						}
					}
					System.out.println("Achou estado destino do Par1["+aux+"]: "+d1.getNome());
					System.out.println("Achou estado destino do Par2["+aux+"]: "+d2.getNome());
					//	Com o estados destinos salvos e hora de verificar se os dois estados destinos sao final/nao-final
					//	Se um for final e o outro nao-final, o par nao pode ser igual e o motivo e armazenado 
					if (d1.estadoFinal != d2.estadoFinal) {
						System.out.println("\nO Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] nao e igual, pois uma das transicoes em "+aux+" vai para final/nao-final!");
						//String aux = parT.getMotivo();
						//System.out.println("Motivo [antes]: "+parT.getMotivo());
						parT.setMotivo(aux+"["+aux1+","+aux2+"]");
						System.out.println("Motivo: "+parT.getMotivo());
						parT.setParFinal(false);
					} else {
						System.out.println("\nO Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] e igual em "+aux+"!");
						//	Como eles são aparentemente iguais, eles são armazenados nos pares dependentes do par analisado
						//	Função que armazena o par na dependência de Par(da1,da2):
						//addParDependente(d1,d2,parT);
					}
					
				//	Um dos estados do par não é completo, informa qual e em qual símbolo
				} else if (parT.par1.transicoes.containsKey(alfabeto.get(i)) == false) {
					System.out.println("ERRO: O estado "+parT.par1.getNome()+" nao tem transicao pro simbolo "+aux+"! VERIFICAR");
					parT.setMotivo("nao-completo");
					return;
				} else {
					System.out.println("ERRO: O estado "+parT.par2.getNome()+" nao tem transicao pro simbolo "+aux+"! VERIFICAR");
					parT.setMotivo("nao-completo");
					return;
				}
			}
			
		}
		
	}
	
	/**
	 * Funcao que cria os pares da tabela
	 */
	public static void criaPares(ArrayList<Estado> estadosT) {
		//	Percorre o array para montar os pares da tabela
		for (int i = 0; i < estadosT.size(); i++) {
			//	Verifica se o indice do array nao e null
			if (estadosT.get(i) != null) { 
				for (int j = 0; j < estadosT.size(); j++) {
					//	Verifica se os estados sao diferentes (final e nao-final) ou nao ((final E final) OU (nao-final e nao-final))
					//	Tambem verifica se os estados em analise são os mesmos, se forem nao formam par
					//	Estado igual forma par
					if (estadosT.get(i).nome.equals(estadosT.get(j).nome) == false) {
						//	Cria objeto Par
						ArrayList<Par> auxDep = new ArrayList<Par>();
						Par parAux = new Par(estadosT.get(i),estadosT.get(j),auxDep,true,null);
						paresD.add(parAux);
					// 	Estados iguais [q1,q1] nao formam par
					} else {
						//System.out.println("\tEstados q["+i+"] e q["+j+"] sao iguais, portanto nao formam par!");
					}
				}		
			// Mensagem de erro caso o objeto no indice i do array seja null
			} else {
				System.out.println("ERRO: Ha erro no index ["+i+"] do ArrayList<Estado> estados! VERIFICAR!\n");
			}
			//System.out.println("\n");
		}
	}
	
	/**
	 * Funcao que encontra e exclui pares repetidos
	 */
	private static void excluiParesRepetidos() {
		//System.out.println("\nEntrou na função excluiParesRepetidos.");
		for (int i = 0; i < paresD.size(); i++) {
			//	Recebe os dois estados do Par[i] e adiciona para obj. Estados auxiliares
			String e1 = paresD.get(i).par1.getNome();
			String e2 = paresD.get(i).par2.getNome();
			
			//System.out.print("PAR["+i+"]: ");
			//imprimePar(paresD.get(i));
			
			//	Com esses dois pares se faz outra varredura na estrutura paresD
			//	Procura-se outro Par formado pelos mesmos estados, mas em ordem contrária
			for (int j = i; j < paresD.size(); j++) {
				//System.out.println("\tEntrou no segundo FOR. Iterador está em ["+j+"]");
				//System.out.print("\tPAR["+j+"]: ");
				//imprimePar(paresD.get(j));
				if (paresD.get(j).par1.getNome().equals(e2) && paresD.get(j).par2.getNome().equals(e1)) {
					//System.out.print("\t\tEntrou no IF.\n");
					//System.out.print("\t\tPAR IGUAL ENCONTRADO!\n");
					paresD.remove(j);
				}
			}
		}
	}

	/**
	 * Funcao que imprime as transições de cada estado do AFD
	 * @param estado Objeto Estado a ser impresso
	 */
	public static void imprimeTransicoes(Estado estado){
		System.out.println(estado.getTransicoes().toString());
	}
	
	public static void main(String[] args) {
		// Insere estados no ArrayList estados
		estados.add(q0);
		estados.add(q1);
		estados.add(q2);
		estados.add(q3);
		estados.add(q4);

		// Insere alfabeto no ArrayList alfabeto
		alfabeto.add('a');
		alfabeto.add('b');

		// Insere estados finais no ArrayList estadosFinais
		estadosFinais.add(q3);

		// Criar AFD
		Automato AFD = new Automato(estados, alfabeto, q0, estadosFinais);

		// Cria estrutura das transições para os estados
		Map<Character, String> tranq0 = new HashMap<Character, String>();
		Map<Character, String> tranq1 = new HashMap<Character, String>();
		Map<Character, String> tranq2 = new HashMap<Character, String>();
		Map<Character, String> tranq3 = new HashMap<Character, String>();
		Map<Character, String> tranq4 = new HashMap<Character, String>();
		
		// Define todas as transições de cada estado
		// Autômato é completo por default
		tranq0.put('a', "q1");
		tranq0.put('b', "q1");
		tranq1.put('a', "q0");
		tranq1.put('b', "q2");
		tranq2.put('a', "q3");
		tranq2.put('b', "q3");
		tranq3.put('a', "q3");
		tranq3.put('b', "q0");
		tranq4.put('b', "q1");

		// Insere o mapa de transições em seus respectivos estados
		q0.setTransicoes(tranq0);
		q1.setTransicoes(tranq1);
		q2.setTransicoes(tranq2);
		q3.setTransicoes(tranq3);
		q4.setTransicoes(tranq4);

		criaPares(estados);
		excluiParesRepetidos();

		int parN = 1;
		for (int i = 0; i < paresD.size(); i++) {
			System.out.println("\nVerificação do Par ["+parN+"]");
			System.out.print(paresD.get(i).par1.getNome()+" = ");
			imprimeTransicoes(paresD.get(i).par1);
			System.out.print(paresD.get(i).par2.getNome()+" = ");
			imprimeTransicoes(paresD.get(i).par2);
			verificaPar(paresD.get(i), AFD.getAlfabeto());
			parN++;
		}
	}

}
