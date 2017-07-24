package minimizacao;

import java.io.*;
import java.util.*;

public class Main {
	static ArrayList<String> definicao = new ArrayList<String>();
	static ArrayList<String> transicoes = new ArrayList<String>();
	static String estados;
	static String alfabeto;
	static String estInicial;
	static String estFinais;
	static Map<String,String> transicoesT = new HashMap<String,String>();
	static ArrayList<String> estadosT = new ArrayList<String>();
	static ArrayList<String> alfabetoAFD = new ArrayList<String>();
	static ArrayList<String> estFinaisT = new ArrayList<String>();
	static ArrayList<Estado> estFinaisAFD = new ArrayList<Estado>();
	//	Estrutura para armazenar todos os estados do AFD
	static ArrayList<Estado> estadosAFD = new ArrayList<Estado>();
	//	Objeto que armazena o estado inicial do AFD
	static Estado estInicialAFD;
	// Estrutura para armazenar todos os pares (D(i,j)) da tabela de minimizacao do AFD
	static ArrayList<Par> paresD = new ArrayList<Par>();
	
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
				(paresD.get(i).par1.equals(e2) && paresD.get(i).par2.equals(e1)))  {
				//System.out.println("Par formado por ["+e1.getNome()+", "+e2.getNome()+"] existe! Adicionar dependencia!");
				paresD.get(i).paresDependentes.add(dependente);
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
	
	/**
	 * Função que encontra e exclui pares repetidos
	 */
	private static void excluiParesRepetidos() {
		//System.out.println("\nEntrou na função excluiParesRepetidos.");
		for (int i = 0; i < paresD.size(); i++) {
			//	Recebe os dois estados do Par[i] e adiciona para obj. Estados auxiliares
			String e1 = paresD.get(i).par1.getNome();
			String e2 = paresD.get(i).par2.getNome();
			
			//System.out.println("PAR["+i+"]: ");
			//imprimePar(paresD.get(i));
			
			//	Com esses dois pares se faz outra varredura na estrutura paresD
			//	Procura-se outro Par formado pelos mesmos estados, mas em ordem contrária
			for (int j = i; j < paresD.size(); j++) {
				//System.out.println("\tEntrou no segundo FOR. Iterador está em ["+j+"]");
				//System.out.println("\tPAR["+j+"]: ");
				//imprimePar(paresD.get(j));
				if (paresD.get(j).par1.getNome().equals(e2) && paresD.get(j).par2.getNome().equals(e1)) {
					//System.out.print("\t\tEntrou no IF.\n");
					//System.out.println("\t\tPAR IGUAL ENCONTRADO!\n");
					paresD.remove(j);
				}
			}
		}
	}
	
	/**
	 * Funcao que imprime todos os pares do AFD, incluindo se sao iguais ou nao e seus pares dependentes!
	 */
	private static void imprimePares() {
		for (int i = 0; i < paresD.size(); i++) {
			System.out.println("\nPAR["+i+"] = ["+paresD.get(i).par1.getNome()+", "+paresD.get(i).par2.getNome()+"]");
			System.out.println("É igual?: "+paresD.get(i).parFinal+" | "+paresD.get(i).getParFinal2());
			System.out.print("Pares Dependentes: ");
			for (int j = 0; j < paresD.get(i).paresDependentes.size(); j++) {
				System.out.print("["+paresD.get(i).paresDependentes.get(j).par1.getNome()+", "+paresD.get(i).paresDependentes.get(j).par2.getNome()+"] ");
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
						//System.out.println("\tEstados q["+i+"] e q["+j+"] são diferentes, portanto formam par!");
						ArrayList<Par> auxDep = new ArrayList<Par>();
						Par parAux = new Par(estadosT.get(i),estadosT.get(j),auxDep,true,0,"");
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
	 * Funcao que analisa a igualdade de transicoes entre os pares
	 * @param parT par a ser analisado
	 * @param alfabeto estrutura que guarda todos os simbolos do alfabeto para analise
	 */
	static public void verificaPar(Par parT, ArrayList<String> alfabeto) {
		Estado d1 = new Estado(null, false, false);
		Estado d2 = new Estado(null, false, false);
		
		//	Verifica se o par não possui transicoes vazia
		if (parT.par1.transicoes.isEmpty() || parT.par2.transicoes.isEmpty()) {
			System.out.println("ERRO: Um dos estados tem o mapa de transicao nulo! VERIFICAR!");
			return;
		//	Verifica se o par e formado por final/nao-final	
		} else if (((parT.par1.isEstadoFinal() == true) && (parT.par2.isEstadoFinal() == false)) || ((parT.par2.isEstadoFinal() == true) && (parT.par1.isEstadoFinal() == false) )) {
			//System.out.println("\nO par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] nao e igual: final/nao-final!");
			parT.setMotivo("final/nao-final");
			parT.setParFinal(false);
			parT.setParFinal2(1);
			return;
		//	Se par e igual verifica transicoes de cada um	
		} else {
			// Verifica se o par tem transicoes completas
			for (int i = 0; i < alfabeto.size(); i++) {
				String aux = alfabeto.get(i);
				if ((parT.getMotivo().equals("final/nao-final") == false)) {
					//	Verificado que o par é não-nulo e completo se verifica se são iguais para todo o alfabeto
					//	Se tudo estiver ok, se inicia a verificacao da igualdade de transicoes para estados iguais
					//	Verifica para transicoes de i (se &(qi,i) = &(qj,i), entao o par pode ser igual)
					//	Armazena o nomes dos estados dos que cada estado do par faz uma transição com i
					String aux1 = parT.par1.transicoes.get(aux);
					String aux2 = parT.par2.transicoes.get(aux);
					//	Verifica se os dois estados destinos sao finais ou nao-finais
					//	Se um for final e o outro nao-final, o par não pode ser igual e o motivo e armazenado 
					for (int j = 0; j < estadosAFD.size(); j++) {
						//System.out.println("Analise de igualdade do par ("+parT.par1.getNome()+", "+parT.par2.getNome()+") na transicao com "+aux+":");
						//	Armazena estado destino do Par1
						if (estadosAFD.get(j).nome.equals(aux1)) {
							d1 = estadosAFD.get(j);							
						}//	Armazena destino do Par2
						if (estadosAFD.get(j).nome.equals(aux2)) {
							d2 = estadosAFD.get(j);
						}
					}
					//System.out.println("Achou estado destino do Par1["+aux+"]: "+d1.getNome());
					//System.out.println("Achou estado destino do Par2["+aux+"]: "+d2.getNome());
					//	Com o estados destinos salvos e hora de verificar se os dois estados destinos sao final/nao-final
					//	Se um for final e o outro nao-final, o par nao pode ser igual e o motivo e armazenado 
					if (d1.estadoFinal != d2.estadoFinal) {
						//System.out.println("\nO Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] nao e igual, em '"+aux+"' vai para final/nao-final!");
						//System.out.println("Motivo [antes]: "+parT.getMotivo());
						parT.setMotivo(aux+"["+aux1+","+aux2+"]");
						//System.out.println("Motivo: "+parT.getMotivo());
						parT.setParFinal(false);
						parT.setParFinal2(1);
						return;
					} else //if ((parT.par1.equals(d1) && parT.par2.equals(d2)) || (parT.par1.equals(d2) && parT.par2.equals(d1))) {
						//System.out.println("\nO Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] e igual em "+aux+"!");
						//return;
					 /*else*/ {
						//System.out.println("\nO Par["+parT.par1.getNome()+", "+parT.par2.getNome()+"] e igual em "+aux+"!");
						//	Como eles são aparentemente iguais, eles são armazenados nos pares dependentes do par analisado
						//	Função que armazena o par na dependência de Par(da1,da2):
						addParDependente(d1,d2,parT);
						return;
					}
				//	Um dos estados do par não é completo, informa qual e em qual símbolo
				} else if (parT.par1.transicoes.containsKey(alfabeto.get(i)) == false) {
					System.out.println("ERRO: O estado "+parT.par1.getNome()+" nao tem transicao pro simbolo "+aux+"! VERIFICAR");
					parT.setParFinal(false);
					parT.setParFinal2(1);
					//parT.setMotivo("nao-completo");
					return;
				} else {
					System.out.println("ERRO: O estado "+parT.par2.getNome()+" nao tem transicao pro simbolo "+aux+"! VERIFICAR");
					parT.setParFinal(false);
					parT.setParFinal2(1);
					//parT.setMotivo("nao-completo");
					return;
				}
			}	
		}
	}
	
	/**
	 * Funcao que gera a tabela de minimizacao do AFD
	 * @throws IOException 
	 */
	private static void geraTabela() throws IOException {	
		String dependentes = "";
		String texto = "INDICE   D[i,j] =           S[i,j] =           MOTIVO";
		System.out.println(texto);
		for (int i = 0; i < paresD.size(); i++) {
			for (int j = 0; j < paresD.get(i).paresDependentes.size(); j++) {
				String aux2 = ("["+paresD.get(i).paresDependentes.get(j).par1.getNome()+","+
						paresD.get(i).paresDependentes.get(j).par2.getNome()+"]");
				dependentes.concat(aux2);
			}
			String aux = "["+paresD.get(i).par1.nome+", "+paresD.get(i).par2.nome+"]   "+
					paresD.get(i).getParFinal2()+"            {"+dependentes+"}     "+
					paresD.get(i).getMotivo();
			System.out.println(aux);
		}
		
		FileWriter arq = new FileWriter("tabela.txt");
	    PrintWriter gravarArq = new PrintWriter(arq);
	    
	    gravarArq.printf("INDICE   D[i,j] =           S[i,j] =           MOTIVO %n");
	    for (int i= 0; i < paresD.size(); i++) {
	      gravarArq.printf("["+paresD.get(i).par1.nome+", "+paresD.get(i).par2.nome+"]   "+
					paresD.get(i).getParFinal2()+"            {[q0,q1],[q2,q1]}     "+
					paresD.get(i).getMotivo()+"%n");
	    }	 
	    arq.close();
	}
	
	/**
	 * Funcao que minimiza o AFD e gera a tabela
	 */
	private static void minimizaAFD() {
		//	Analisa cada par do AFD, se for igual, pares dependentes sao iguais
		//	Senao for, pares dependentes nao sao iguais
		for (int i = 0; i < paresD.size(); i++) {
			//	Se par e igual, seus dependetes sao iguais
			if (paresD.get(i).parFinal == true) {
				for (int j = 0; j < paresD.get(i).paresDependentes.size(); j++) {
					paresD.get(i).paresDependentes.get(j).setParFinal(true);
					paresD.get(i).paresDependentes.get(j).setParFinal2(0);
				}
			//	Se o par nao e igual seus dependentes tb nao sao iguais	
			//	Muda tb o motivo de nao ser igual de cada par dependente dele	
			} else {
				for (int j = 0; j < paresD.get(i).paresDependentes.size(); j++) {
					paresD.get(i).paresDependentes.get(j).setParFinal(false);
					paresD.get(i).paresDependentes.get(j).setParFinal2(1);
					paresD.get(i).paresDependentes.get(j).setMotivo("prop["+paresD.get(i).paresDependentes.get(j).par1.nome
							+","+paresD.get(i).paresDependentes.get(j).par2.nome+"]");
				}
			}
		}
		// Modifica o arquivo tabela.txt
		//geraTabela();
		
		//	Junta todos os pares iguais em um so estado
	}
	
	public static void limpaTudo() {
		definicao.clear();		transicoes.clear();
		estados = null;		alfabeto = null;
		estInicial = null;		estFinais = null;
		transicoesT.clear();		estadosT.clear();
		alfabetoAFD.clear(); estFinaisT.clear();
		estFinaisAFD.clear(); estadosAFD.clear();
		estInicialAFD = null; paresD.clear();
	}
	
	//	INICIO - FUNCOES QUE INTERAGEM COM O ARQUIVO E CRIAM AS ESTRUTURAS PRO AFD
	public static void criaEstados() {
		//	Limpa estados
		estados = estados.replaceAll("}", " ");
		estados = estados.replaceAll("\\{", " ");
		estados = estados.trim();
		estados = estados.replaceAll(",", " ");
		//System.out.println("Estados:"+estados);
		
		//	Separa estados
		String[] aux = estados.split(" "); // split according to the hyphen and put them in an array
		for(String subString : aux){ // cycle through the array
		   //System.out.print(subString);
		   //	Cria estados
		   subString = subString.trim();
		   estadosT.add(subString);
		}
	}
	
	public static void criaAlfabeto() {
		//	Limpa alfabeto
		alfabeto = alfabeto.replaceAll("}", " ");
		alfabeto = alfabeto.replaceAll("\\{", " ");
		alfabeto = alfabeto.trim();
		alfabeto = alfabeto.replaceAll(",", " ");
		//System.out.println("Alfabeto:"+alfabeto);
		
		//	Separa alfabeto
		String[] aux = alfabeto.split(" "); // split according to the hyphen and put them in an array
		for(String subString : aux){ // cycle through the array
			//System.out.print(subString+" ");
			//	Cria alfabeto 
			subString = subString.trim();
		    alfabetoAFD.add(subString);
		}
	}
	
	public static void criaEstadoInicial() {
		//	Limpa estado inicial
		estInicial = estInicial.replaceAll(",", "");
		estInicial = estInicial.trim();
		//System.out.println("Estado Inicial:"+estInicial);
	}
	
	public static void criaEstadosFinais() {
		//	Limpa estados finais
		estFinais = estFinais.replaceAll("}", " ");
		estFinais = estFinais.replaceAll("\\{", " ");
		estFinais = estFinais.trim();
		estFinais = estFinais.replaceAll(",", " ");
		//System.out.println("Estados Finais:"+estFinais);
		
		//	Separa estados finais
		String[] aux = estFinais.split(" "); // split according to the hyphen and put them in an array
		for(String subString : aux){ // cycle through the array
		    //System.out.print(subString+" ");
		    //	Cria estados finais
			subString = subString.trim();
			estFinaisT.add(subString);
		}
	}
	
	public static void criaTransicoes() {
		String aux1; //String aux2;
		//	Limpa as transicoes
		for (int i = 0; i < transicoes.size(); i++) {
			//System.out.println("Transicao ["+i+"]: "+transicoes.get(i));
			aux1 = transicoes.get(i).replaceAll("\\(","");
			aux1 = aux1.replaceAll("\\)","");
			aux1 = aux1.replaceAll(","," ");
			//System.out.println("Transicao ["+i+"]: "+aux1);
			
			//	Separa as transições finais
			String[] aux = aux1.split(" "); // split according to the hyphen and put them in an array
			String estInit = aux[0];
			estInit = estInit.trim();
			//System.out.println(estInit);
			String tran = aux[1];
			tran = tran.trim();
			tran = tran.replace("->", " ");
			//System.out.println(tran);
			String[] aux2 = tran.split(" ");
			String simbolo = aux2[0];
			simbolo = simbolo.trim();
			//System.out.println(simbolo);
			String estFim = aux2[1];
			estFim = estFim.trim();
			
			for (int j = 0; j < estadosAFD.size(); j++) {
				 if (estadosAFD.get(j).getNome().equals(estInit)) {
					// System.out.println("ENTROU");
					 estadosAFD.get(j).transicoes.put(simbolo, estFim);
					 //System.out.println("\nEstado: "+estadosAFD.get(j).getNome()+"\nTransicao: "+simbolo+" -> "+estadosAFD.get(j).getTransicoes().get(simbolo));
				 }
			}	
		}
	}
	
	public static void criaObjEstados() {
		//	Verifica se array dos estados não é vazio
		if (estadosT.isEmpty() == false) {
			//	cria objetos Estado
			for (int i = 0; i < estadosT.size(); i++) {
				//	Criação do estado inicial
				if (estadosT.get(i).equals("") == false) {
					Estado est = new Estado(estadosT.get(i), false, false);
					estadosAFD.add(est);
				}
			}
		}
		
		//	Cria estado Inicial 
		for (int i = 0; i < estadosAFD.size(); i++) {
			if (estadosAFD.get(i).getNome().equals(estInicial)) {
				estadosAFD.get(i).setEstadoInicial(true);
				estInicialAFD = estadosAFD.get(i);
			}
		}

		//	Cria os estados finais
		for (int i = 0; i < estadosAFD.size(); i++) {
			if (estFinaisT.contains(estadosAFD.get(i).getNome())) {
				estadosAFD.get(i).setEstadoFinal(true);
				estFinaisAFD.add(estadosAFD.get(i));
			}
		}
		
		/*System.out.println("\nEstados:");
		//	Imprime estados do AFD
		for (int i = 0; i < estadosAFD.size(); i++) {
			estadosAFD.get(i).imprimeEstado();
		}	
		
		System.out.println("\nEstados Inicial:");
		//	Imprime estado inicial do AFD
		estInicialAFD.imprimeEstado();
		
		System.out.println("\nEstados Finais:");
		//	Imprime estados finais do AFD
		for (int i = 0; i < estFinaisAFD.size(); i++) {
			estFinaisAFD.get(i).imprimeEstado();
		}*/
	}
	
	public static void limpaDefinicao(ArrayList<String> AFD) {
		estados = AFD.get(1);
		//System.out.println("Estados: "+estados);
		alfabeto = AFD.get(2);
		//System.out.println("Alfabeto: "+alfabeto);
		int tam = AFD.size();
		estInicial = AFD.get(tam-3);
		//System.out.println("Estado Inicial: "+estInicial);
		estFinais = AFD.get(tam-2);
		//System.out.println("Estados Finais: "+estFinais);
		for (int i = 4; i < tam-4; i++) {
			transicoes.add(AFD.get(i));
			//System.out.println("Transicao ["+(i-4)+"]: "+AFD.get(i));
		}
		
		//System.out.println("\n");
		criaEstados();
		//System.out.println("\n");
		criaAlfabeto();
		//System.out.println("\n");
		criaEstadoInicial();
		//System.out.println("");
		criaEstadosFinais();
		System.out.println("\n");
		criaObjEstados();
		//System.out.println("\n");
		criaTransicoes();
		System.out.println("\n");
	}
	//	FIM - FUNCOES QUE INTERAGEM COM O ARQUIVO E CRIAM AS ESTRUTURAS PRO AFD	
	
	/**
	 * Função principal do programa.
	 * @param args argumentos da linha de comando.
	 */
	public static void main (String[] args)throws IOException {
		try {
			File arquivoEntrada = new File("desc_af1.txt");
			Reader entrada = new FileReader(arquivoEntrada);
			LineNumberReader reader = new LineNumberReader(entrada);
			
			while(reader.ready()){
				definicao.add(reader.readLine());
			}
			reader.close();
			entrada.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("ERRO: Arquivo não existe! VERIFICAR!");
		}
		
		//	Arruma toda a string recebida do arquivo 
		limpaDefinicao(definicao);
		
		//	Cria o AFD para minimizar
		Automato AFD = new Automato(estadosAFD, alfabetoAFD, estInicialAFD, estFinaisAFD);
		
		//	Minimiza
		criaPares(estadosAFD);
		excluiParesRepetidos();
		
		int parN = 1;
		for (int i = 0; i < paresD.size(); i++) {
			//System.out.println("\nVerificação do Par["+parN+"]");
			//System.out.print(paresD.get(i).par1.getNome()+" = ");
			//imprimeTransicoes(paresD.get(i).par1);
			//System.out.print(paresD.get(i).par2.getNome()+" = ");
			//imprimeTransicoes(paresD.get(i).par2);
			verificaPar(paresD.get(i), AFD.getAlfabeto());
			parN++;
		}
		
		//imprimePares();	
		minimizaAFD();
		geraTabela();
		
		FileWriter arq2 = new FileWriter("afd_minimizado.txt");
	    PrintWriter gravarArq2 = new PrintWriter(arq2);
	    arq2.close();
	    
	    //	Zera todas as estruturas globais
	    limpaTudo();
	}
}