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
public class Testes {
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
	//	FIM VARI�VEIS GLOBAIS
	
	/*
	 * Fun��o que imprime as transi��es de cada estado do AFD
	 */
	public static void imprimeTransicoes(Estado estado){
		System.out.println(estado.getTransicoes().toString());
	}
	
	/**
	 * Fun��o que analisa a igualdade de transi��es entre os pares
	 * @param paresT
	 */
	public static void verificaPar(Par parT) {
		Estado da1 = new Estado(null, false, false, null);
		Estado da2 = new Estado(null, false, false, null);
		Estado db1 = new Estado(null, false, false, null);
		Estado db2 = new Estado(null, false, false, null);
		
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
						//System.out.println("An�lise de Igualdade do par ("+parT.par1.getNome()+", "+parT.par2.getNome()+") na transi��o com 'a':");
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
						//System.out.println("An�lise de Igualdade do par ("+parT.par1.getNome()+", "+parT.par2.getNome()+") na transi��o com 'a':");
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
						System.out.println("O Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] n�o � igual, pois uma das transi��es em 'a' vai para final e ou outro para n�o final!");
						//String aux = parT.getMotivo();
						//System.out.println("Motivo [antes]: "+parT.getMotivo());
						parT.setMotivo("a {"+da1.getNome()+","+da2.getNome()+"}");
						System.out.println("Motivo: "+parT.getMotivo());
						parT.setParFinal(false);
						
					} else {
						System.out.println("O Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] � igual em 'a'!");
						//	Como eles s�o aparentemente iguais, eles s�o armazenados nos pares dependentes do par analisado
						//	... FAZER
					}
					//	Verifica��o para as transi��es com 'b'
					if (db1.estadoFinal != db2.estadoFinal) {
						System.out.println("O Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] n�o � igual, pois uma das transi��es em 'b' vai para final e ou outro para n�o final!");
						//String aux = parT.getMotivo();
						//System.out.println("Motivo [antes]: "+aux);
						parT.setMotivo("a {"+db1.getNome()+","+db2.getNome()+"}");
						System.out.println("Motivo: "+parT.getMotivo());
						parT.setParFinal(false);
					} else {
						System.out.println("O Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] � igual em 'b'!");
						//	Como eles s�o aparentemente iguais, eles s�o armazenados nos pares dependentes do par analisado
						//	... FAZER
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
	 * Fun��o que cria a tabela de minimiza��o
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
					if (((estadosT.get(i).isEstadoFinal() == true & estadosT.get(j).isEstadoFinal() == true)
					 ||(estadosT.get(i).isEstadoFinal() == false & estadosT.get(j).isEstadoFinal() == false))
					 && (estadosT.get(i).nome.equals(estadosT.get(j).nome) == false)) {
						System.out.println("\tEstados q["+i+"] e q["+j+"] s�o iguais, portanto formam par!");
						//	Cria objeto Par
						Par parAux = new Par(estadosT.get(i),estadosT.get(j),null,false,null);
						paresD.add(parAux);
					//	Estados diferentes (final e n�o-final) n�o forma par
					// 	Estados iguais (mesmo estado {q1,q1}) tamb�m n�o forma par
					} else {
						System.out.println("\tEstados q["+i+"] e q["+j+"] s�o diferentes, portanto n�o formam par!");
					}
				}
			// Mensagem de erro caso o objeto no �ndice i do array seja null
			} else {
				System.out.println("ERRO: H� erro no index ["+i+"] do ArrayList<Estado> estados!\nVERIFICAR!\n");
			}
			System.out.println("\n");
		}
	}
	
	public static void main(String[] args) {
		Map<Character,String> tranq0 = new HashMap<Character,String>();
		Map<Character,String> tranq1 = new HashMap<Character,String>();
		Map<Character,String> tranq2 = new HashMap<Character,String>();
		Map<Character,String> tranq3 = new HashMap<Character,String>();
		
		//	Insere estados no ArrayList estados
		estados.add(q0);
		estados.add(q1);
		estados.add(q2);
		estados.add(q3);
		
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
		
		//	Insere o mapa de transi��es em seus respectivos estados
		q0.setTransicoes(tranq0);
		q1.setTransicoes(tranq1);
		q2.setTransicoes(tranq2);
		q3.setTransicoes(tranq3);	
		
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
		
		//	Analisa o ArrayList estados e verifica quais estados s�o iguais ou n�o
		//	Estados iguais formam pares para an�lise do algoritmo
		criaPares(estados);
		
		//	Testa cria��o de pares (D(i,j))
		System.out.println("D(i,j)= ");
		for (int i = 0; i < paresD.size(); i++) {
			System.out.println("["+paresD.get(i).par1.getNome()+" , "+paresD.get(i).par2.getNome()+"]");
		}	
		
		//	Com a tabela pronta � hora de verificar a igualdade de transi��es entre os estados pares
		//	Percorre o array de pares e verifica as transi��es de cada um
		int parN = 1;
		System.out.println("\nVerifica��o da Igualdade de Transi��es entre os Pares do AFD");
		for (int i = 0; i < paresD.size(); i++) {
			System.out.println("\nVerifica��o do Par ["+parN+"]");
			System.out.print(paresD.get(i).par1.getNome()+" = ");
			imprimeTransicoes(paresD.get(i).par1);
			System.out.print(paresD.get(i).par2.getNome()+" = ");
			imprimeTransicoes(paresD.get(i).par2);
			//	Chama fun��o que analisa a igualdade de transi��es entre os pares
			verificaPar(paresD.get(i));
			parN++;
		}
	}

}