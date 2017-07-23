package minimizacao;

import java.io.*;
import java.util.*;

public class Main {
	static ArrayList<Estado> estadosAFD = new ArrayList<Estado>();
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
	static Estado estInicialAFD;
	
	public static void criaEstados() {
		//	Limpa estados
		estados = estados.replaceAll("}", " ");
		estados = estados.replaceAll("\\{", " ");
		estados = estados.trim();
		estados = estados.replaceAll(",", " ");
		System.out.println("Estados:"+estados);
		
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
		System.out.println("Alfabeto:"+alfabeto);
		
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
		System.out.println("Estado Inicial:"+estInicial);
	}
	
	public static void criaEstadosFinais() {
		//	Limpa estados finais
		estFinais = estFinais.replaceAll("}", " ");
		estFinais = estFinais.replaceAll("\\{", " ");
		estFinais = estFinais.trim();
		estFinais = estFinais.replaceAll(",", " ");
		System.out.println("Estados Finais:"+estFinais);
		
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
			for(String subString : aux){ // cycle through the array
				//System.out.println(subString);
				//tranN.put(s, arg1)
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
					Estado est = new Estado(estadosT.get(i), false, false, null);
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
		
		System.out.println("\nEstados:");
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
		}
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
		//System.out.println("\n");
		criaTransicoes();
		System.out.println("\n");
		criaObjEstados();
	}
	
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
		
		limpaDefinicao(definicao);
		//Automato AFD = new Automato(estadosAFD, alfabetoAFD, estInicialAFD, estFinaisAFD,null);
		
		
	}
	

}