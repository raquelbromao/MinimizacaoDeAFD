package minimizacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe que gerencia os arquivos e as fun��es do algoritmo de minimiza��o 
 *
 * @author Raquel
 *
 */
public class Minimizacao {
	//	IN�CIO VARI�VEIS GLOBAIS
	//	Estado Inicial
	static Estado q0 = new Estado("q0",false,true,null);
	//	Estados nem iniciais e nem finais
	static Estado q1 = new Estado("q1",false,false,null);
	static Estado q2 = new Estado("q2",false,false,null);
	//	Estado Final
	static Estado q3 = new Estado("q3",true,false,null);	
	//	Estrutura para armazenar todos os estados do AFD
	static ArrayList<Estado> estados = new ArrayList<Estado>();
	// Estrutura para armazenar todos os pares (D(i,j)) da Tabela de minimiza��o
	static ArrayList<Par> paresD = new ArrayList<Par>();
	//	Estrutura que armazena o alfabeto do AFD
	static ArrayList<Character> alfabeto = new ArrayList<Character>();
//	Estrutura para armazenar todos os estados finais do AFD
	static ArrayList<Estado> estadosFinais = new ArrayList<Estado>();
	//	FIM VARI�VEIS GLOBAIS
	
	private static void imprimePares() {
		for (int i = 0; i < paresD.size(); i++) {
			System.out.println("PAR["+i+"] :");
			imprimePar(paresD.get(i));
			System.out.println("\n\t� igual?: "+paresD.get(i).parFinal);
			System.out.print("\tPares Dependentes: ");
			for (int j = 0; j < paresD.get(i).paresDependentes.size(); j++) {
				imprimePar(paresD.get(i).paresDependentes.get(j));
			}
		}
	}
	
	/**
	 * Fun��o que imprime as transi��es de cada estado do AFD
	 * @param estado Objeto Estado a ser impresso
	 */
	public static void imprimeTransicoes(Estado estado){
		System.out.println(estado.getTransicoes().toString());
	}
	
	/**
	 * Fun��o que imprime os estados de cada Par
	 * @param parAux Objeto Par a ser impresso
	 */
	public static void imprimePar(Par parAux){
		System.out.println("["+parAux.par1.getNome()+" , "+parAux.par2.getNome()+"]");
	}
	
	/**
	 * Fun��o que adiciona aos Pares do AFD os pares que dependem deles para serem iguais
	 * @param e1 Objeto Estado do Par
	 * @param e2 Objeto Estado do Par
	 * @param dependente Objeto Par que depende do Par(e1,e2) para ser igual
	 */
	public static void addParDependente(Estado e1, Estado e2, Par dependente) {
		//	Verifica se esse par existe (par formado pelo estado x1 e x2)
		//	Se existir adiciona o Par dependente a estrutura paresDependentes
		for (int i = 0; i < paresD.size(); i++) {
			//	Verifica exist�ncia do par formado por x1 e x2 -> Par(x1,x2) OU Par(x2,x1)
			if ((paresD.get(i).par1.equals(e1) && paresD.get(i).par2.equals(e2)) || 
				(paresD.get(i).par1.equals(e2) && paresD.get(i).par2.equals(e1))) {
				System.out.println("Par formado por ["+e1.getNome()+", "+e2.getNome()+"] existe! Adicionar depend�ncia!");
				//	Teste da estrutura paresDependentes pr� inser��o
				//System.out.print("\tPares Dependentes Antes: ");
				/*for (i = 0; i < paresD.get(i).paresDependentes.size(); i++) {
					imprimePar(paresD.get(i));
				}*/

				paresD.get(i).paresDependentes.add(dependente);
				//	Teste da estrutura paresDependentes p�s inser��o
				//System.out.println("\tPares Dependentes Depois: ");
				/*for (i = 0; i < paresD.get(i).paresDependentes.size(); i++) {
					imprimePar(paresD.get(i));
				}*/
			} else {
				//System.out.println("ERRO: Este par {"+x1.getNome()+", "+x2.getNome()+"} n�o existe!\nVERIFICAR!\n");
			}
		}
	}
	
	/**
	 * Fun��o que analisa a igualdade de transi��es entre os pares
	 * @param paresT
	 */
	public static void verificaPar(Par parT, ArrayList<Character> alfabeto) {
		Estado da1 = new Estado(null, false, false, null);
		Estado da2 = new Estado(null, false, false, null);
		Estado db1 = new Estado(null, false, false, null);
		Estado db2 = new Estado(null, false, false, null);
		
		//	Verifica se o pares s�o finais/n�o-finais. Se forem n�o s�o iguais e atualiza o motivo
		if ((parT.par1.estadoFinal == true && parT.par2.estadoFinal == false) 
		 || (parT.par1.estadoFinal == false && parT.par2.estadoFinal == true)) {
			parT.setParFinal(false);
			parT.setMotivo("final/nao-final");
		}
		
		//	In�cio da Verifica��o da igualdade entre as transi��es do estado do par
		//	Verifica se h� algum erro no mapa de transi��es de casa estado do par
		if (parT.par1.transicoes.isEmpty() || parT.par2.transicoes.isEmpty()) {
			System.out.println("ERRO: Um dos estados tem o mapa de transi��es NULO!\nVERIFICAR!\n");
			//	Caso n�o ocorra erro no mapa de transi��es em nenhum dos estados do par	continua a verifica��o
		} else {
			//	Verifica se os dois estados tem transi��es de 'a', se um n�o tiver eles s�o diferentes
			if ((parT.par1.transicoes.containsKey('a') == true) && (parT.par2.transicoes.containsKey('a'))) {
				//	Verifica se os dois estados tem transi��es de 'b', se um n�o tiver eles s�o diferentes
				if ((parT.par1.transicoes.containsKey('b') == true) && (parT.par2.transicoes.containsKey('b'))) {
					//	Se tudo estiver ok, se inicia a verifica��o da igualdade de transi��es para estados iguais
					//	Verifica para transi��es de 'a' (se &(qi,a) = &(qj,a), ent�o o par pode ser igual)
					//	Armazena o nomes dos estados dos que cada estado do par faz uma transi��o com 'a'
					String aux1 = parT.par1.transicoes.get('a');
					String aux2 = parT.par2.transicoes.get('a');
					//	Verifica se os dois estados destinos s�o finais ou n�o-finais
					//	Se um for final e o outro n�o-final, o par n�o pode ser igual e o motivo � armazenado 
					for (int i = 0; i < estados.size(); i++) {
						System.out.println("An�lise de Igualdade do par ("+parT.par1.getNome()+", "+parT.par2.getNome()+") na transi��o com 'a':");
						//	Armazena estado destino do Par1
						if (estados.get(i).nome.equals(aux1)) {
							da1 = estados.get(i);							
						}
						//	Armazena destino do Par2
						if ((estados.get(i).nome.equals(aux2)) && (estados.get(i) !=  da1)) {
							da2 = estados.get(i);
						}
					}
					System.out.println("Achou estado destino do Par1[a]: "+da1.getNome());
					System.out.println("Achou estado destino do Par2[a]: "+da2.getNome());
					
					//	Verifica para transi��es de 'b' (se &(qi,b) = &(qj,b), ent�o o par pode ser igual)
					//	Armazena o nomes dos estados dos que cada estado do par faz uma transi��o com 'a'
					aux1 = parT.par1.transicoes.get('b');
					aux2 = parT.par2.transicoes.get('b');
					//	Armazena os estados destinos das transi��es para checagem
					for (int i = 0; i < estados.size(); i++) {
						System.out.println("An�lise de Igualdade do par ("+parT.par1.getNome()+", "+parT.par2.getNome()+") na transi��o com 'a':");
						//	Armazena estado destino do Par1
						if (estados.get(i).nome.equals(aux1)) {
							db1 = estados.get(i);							
						}
						//	Armazena destino do Par2
						if ((estados.get(i).nome.equals(aux2)) && (estados.get(i) !=  db1)) {
							db2 = estados.get(i);
						}
					}
					System.out.println("Achou estado destino do Par1[b]: "+db1.getNome());
					System.out.println("Achou estado destino do Par2[b]: "+db2.getNome());
					
					//	Com o estados destinos salvos � hora de verificar
					//	Verifica se os dois estados destinos s�o finais ou n�o-finais
					//	Se um for final e o outro n�o-final, o par n�o pode ser igual e o motivo � armazenado 
					//	Verifica��o para as transi��es com 'a'
					if (da1.estadoFinal != da2.estadoFinal) {
						System.out.println("\nO Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] n�o � igual, pois uma das transi��es em 'a' vai para final e ou outro para n�o final!");
						//String aux = parT.getMotivo();
						//System.out.println("Motivo [antes]: "+parT.getMotivo());
						parT.setMotivo("a["+da1.getNome()+","+da2.getNome()+"]");
						System.out.println("Motivo: "+parT.getMotivo());
						parT.setParFinal(false);
						
					} else {
						System.out.println("\nO Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] � igual em 'a'!");
						//	Como eles s�o aparentemente iguais, eles s�o armazenados nos pares dependentes do par analisado
						//	Fun��o que armazena o par na depend�ncia de Par(da1,da2):
						addParDependente(da1,da2,parT);
					}
					//	Verifica��o para as transi��es com 'b'
					if (db1.estadoFinal != db2.estadoFinal) {
						System.out.println("\nO Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] n�o � igual, pois uma das transi��es em 'b' vai para final e ou outro para n�o final!");
						//String aux = parT.getMotivo();
						//System.out.println("Motivo [antes]: "+aux);
						parT.setMotivo("b["+db1.getNome()+","+db2.getNome()+"]");
						System.out.println("Motivo: "+parT.getMotivo());
						parT.setParFinal(false);
					} else {
						System.out.println("\nO Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] � igual em 'b'!");
						//	Como eles s�o aparentemente iguais, eles s�o armazenados nos pares dependentes do par analisado
						//	Fun��o que armazena o par na depend�ncia de Par(da1,da2):
						addParDependente(db1,db2,parT);
					}
				} else {
					System.out.println("Par n�o � igual!\nUm deles n�o possui transi��o de 'b'\n");
				}
			} else {
				System.out.println("Par n�o � igual!\nUm deles n�o possui transi��o de 'a'\n");
			}
		}
	}
	
	/**
	 * Fun��o que cria pares da tabela
	 */
	public static void criaPares(ArrayList<Estado> estadosT) {
		System.out.println("An�lise de Pares:");
		//	Percorre o array para montar os pares da tabela
		for (int i = 0; i < estadosT.size(); i++) {
			//	Verifica se o �ndice do array n�o � null
			if (estadosT.get(i) != null) { 
				for (int j = 0; j < estadosT.size(); j++) {
					//	Verifica se os estados s�o diferentes (final e n�o-final) ou n�o ((final E final) OU (n�o-final e n�o-final))
					//	Tamb�m verifica se os estados em an�lise s�o o mesmo, se for n�o formam par
					//	Estado igual forma par
					if (estadosT.get(i).nome.equals(estadosT.get(j).nome) == false) {
						System.out.println("\tEstados q["+i+"] e q["+j+"] s�o diferentes, portanto formam par!");
						//	Cria objeto Par
						ArrayList<Par> auxDep = new ArrayList<Par>();
						Par parAux = new Par(estadosT.get(i),estadosT.get(j),auxDep,true,0,null);
						paresD.add(parAux);
					// 	Estados iguais [q1,q1] n�o formam par
					} else {
						System.out.println("\tEstados q["+i+"] e q["+j+"] s�o iguais, portanto n�o formam par!");
					}
				}
			// Mensagem de erro caso o objeto no �ndice i do array seja null
			} else {
				System.out.println("ERRO: H� erro no index ["+i+"] do ArrayList<Estado> estados! VERIFICAR!\n");
			}
			System.out.println("\n");
		}
	}
	
	/**
	 * Fun��o que encontra e exclui pares repetidos
	 */
	private static void excluiParesRepetidos() {
		//System.out.println("\nEntrou na fun��o excluiParesRepetidos.");
		for (int i = 0; i < paresD.size(); i++) {
			//	Recebe os dois estados do Par[i] e adiciona para obj. Estados auxiliares
			String e1 = paresD.get(i).par1.getNome();
			String e2 = paresD.get(i).par2.getNome();
			
			//System.out.print("PAR["+i+"]: ");
			//imprimePar(paresD.get(i));
			
			//	Com esses dois pares se faz outra varredura na estrutura paresD
			//	Procura-se outro Par formado pelos mesmos estados, mas em ordem contr�ria
			for (int j = i; j < paresD.size(); j++) {
				//System.out.println("\tEntrou no segundo FOR. Iterador est� em ["+j+"]");
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
	
	private static void minimizaAFD() {
		//	Analisa cada Par, se for igual, pares dependentes s�o iguais
		//	Sen�o for, pares dependentes n�o s�o iguais
		for (int i = 0; i < paresD.size(); i++) {
			//	Analisa se o par � igual, se for entra na condi��o
			if (paresD.get(i).parFinal == true) {
				//	Sabendo que o par � igual, todos os seus pares dependentes s�o iguais
				for (int j = 0; j < paresD.get(i).paresDependentes.size(); j++) {
					paresD.get(i).paresDependentes.get(j).setParFinal(true);
				}
				
				//	Feitas as mudan�as apropriadas do atributo parFinal para todos os pares do AFD
				//	S�o feitas as mudan�as no AFD juntando os estados iguais
				
			// Par n�o � igual entra em else	
			} else {
				//	Sabendo que o par n�o � igual, todos os seus pares dependentes n�o s�o iguais
				//	Atualiza os motivos de cada
				for (int j = 0; j < paresD.get(i).paresDependentes.size(); j++) {
					paresD.get(i).paresDependentes.get(j).setParFinal(false);
					paresD.get(i).paresDependentes.get(j).setMotivo("prop["+paresD.get(i).paresDependentes.get(j).par1.nome
																		+","+paresD.get(i).paresDependentes.get(j).par2.nome+"]");
				}
			}
			
			//	Feitas as mudan�as apropriadas do atributo parFinal para todos os pares do AFD
			//	S�o feitas as mudan�as no AFD juntando os estados iguais	
			//	Analisa se par � Igual
			if (paresD.get(i).isParFinal() == true) {
				//String nome = pa
			}
		}
	}
	
	public static void main(String[] args) {
		//	Insere estados no ArrayList estados
		estados.add(q0);
		estados.add(q1);
		estados.add(q2);
		estados.add(q3);		
		//estados.add(q4);
		//estados.add(q5);
		//estados.add(q6);
		
		//	Insere alfabeto no ArrayList alfabeto
		alfabeto.add('a');
		alfabeto.add('b');
		
		//	Insere estados finais no ArrayList estadosFinais
		estadosFinais.add(q3);
		
		//	Criar AFD
		Automato AFD = new Automato(estados, alfabeto, q0, estadosFinais);
		
		//	Cria estrutura das transi��es para os estados
		Map<Character,String> tranq0 = new HashMap<Character,String>();
		Map<Character,String> tranq1 = new HashMap<Character,String>();
		Map<Character,String> tranq2 = new HashMap<Character,String>();
		Map<Character,String> tranq3 = new HashMap<Character,String>();
		//Map<Character,String> tranq4 = new HashMap<Character,String>();
		//Map<Character,String> tranq5 = new HashMap<Character,String>();
		//Map<Character,String> tranq6 = new HashMap<Character,String>();

		
		//	Define todas as transi��es de cada estado
		//	Aut�mato � completo por default
		tranq0.put('a', "q1");
		tranq0.put('b', "q1");
		tranq1.put('a', "q0");
		tranq1.put('b', "q2");
		tranq2.put('a', "q3");
		tranq2.put('b', "q3");
		tranq3.put('a', "q3");
		tranq3.put('b', "q0");
		//tranq4.put('a', "q4");
		//tranq4.put('b', "q4");
		//tranq5.put('a', "q5");
		//tranq5.put('b', "q5");
		//tranq6.put('a', "q6");
		//tranq6.put('b', "q6");

		
		//	Insere o mapa de transi��es em seus respectivos estados
		q0.setTransicoes(tranq0);
		q1.setTransicoes(tranq1);
		q2.setTransicoes(tranq2);
		q3.setTransicoes(tranq3);	
		//q4.setTransicoes(tranq4);	
		//q5.setTransicoes(tranq5);	
		//q6.setTransicoes(tranq6);	
		
		//	Imprime os estados
		System.out.println("Transi��es do Q0:");
		imprimeTransicoes(q0);
		System.out.println("� final? "+q0.isEstadoFinal());
		System.out.println("� inicial? "+q0.isEstadoInicial()+"\n");
		System.out.println("Transi��es do Q1:");
		imprimeTransicoes(q1);
		System.out.println("� final? "+q1.isEstadoFinal());
		System.out.println("� inicial? "+q1.isEstadoInicial()+"\n");
		System.out.println("Transi��es do Q2:");
		imprimeTransicoes(q2);
		System.out.println("� final? "+q2.isEstadoFinal());
		System.out.println("� inicial? "+q2.isEstadoInicial()+"\n");
		System.out.println("Transi��es do Q3:");
		imprimeTransicoes(q3);
		System.out.println("� final? "+q3.isEstadoFinal());
		System.out.println("� inicial? "+q3.isEstadoInicial()+"\n");
		/*
		System.out.println("� final? "+q4.isEstadoFinal());
		System.out.println("� inicial? "+q4.isEstadoInicial()+"\n");
		System.out.println("Transi��es do Q4:");
		imprimeTransicoes(q4);
		System.out.println("� final? "+q5.isEstadoFinal());
		System.out.println("� inicial? "+q5.isEstadoInicial()+"\n");
		System.out.println("Transi��es do Q5:");
		imprimeTransicoes(q5);
		System.out.println("� final? "+q6.isEstadoFinal());
		System.out.println("� inicial? "+q6.isEstadoInicial()+"\n");
		System.out.println("Transi��es do Q6:");
		imprimeTransicoes(q6);
		*/
		
		//	Analisa o ArrayList estados e verifica quais estados s�o iguais ou n�o
		//	Estados iguais formam pares para an�lise do algoritmo
		criaPares(estados);
		//	Dos pares criados anteriormente s�o exclu�dos os pares repetidos: ParN[x1,x2] e ParM[x2,x1] -> ParM ser� exclu�do
		excluiParesRepetidos();
		
		//	Testa cria��o de pares (D(i,j))
		System.out.println("\nD(i,j)= ");
		for (int i = 0; i < paresD.size(); i++) {
			imprimePar(paresD.get(i));
		}
		
		//	Com a tabela pronta � hora de verificar a igualdade de transi��es entre os estados pares
		//	Percorre o array de pares e verifica as transi��es de cada um
		int parN = 1;
		//System.out.println("\nVerifica��o da Igualdade de Transi��es entre os Pares do AFD");
		for (int i = 0; i < paresD.size(); i++) {
		//	System.out.println("\nVerifica��o do Par ["+parN+"]");
			//System.out.print(paresD.get(i).par1.getNome()+" = ");
			//imprimeTransicoes(paresD.get(i).par1);
			//System.out.print(paresD.get(i).par2.getNome()+" = ");
			//imprimeTransicoes(paresD.get(i).par2);
			//	Chama fun��o que analisa a igualdade de transi��es entre os pares
			verificaPar(paresD.get(i), AFD.getAlfabeto());
			parN++;
		}
		
		
		imprimePares();
		//minimizaAFD();
		
		
		//	Limpa
		paresD.clear();
		estados.clear();
	}
}