package minimizacao;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que manipula e gerencia os estados do AFD
 * 
 * @author Raquel
 *
 */
public class Estado {
	// String com o nome do estado
	String nome;	
	//	Define se estado � final e inicial
	//	Estado inicial = TRUE
	//	Estado final = TRUE
	boolean estadoInicial;
	boolean estadoFinal;	
	//	MapaDeTransi��es<CHAVE: VALOR DA TRANSI��O, VALOR: ESTADO>
	//Map<Character,String> transicoes = new HashMap<Character,String>();
	Map<String,String> transicoes = new HashMap<String,String>();

	/**
	 * Construtor do objeto tipo Estado
	 * @param nome
	 * @param estadoFinal
	 * @param estadoInicial
	 * @param transicoes
	 */
	public Estado(String nome, boolean estadoFinal, boolean estadoInicial) {
		super();
		this.nome = nome;
		this.estadoFinal = estadoFinal;
		this.estadoInicial = estadoInicial;
		Map<String,String> aux = new HashMap<String,String>();
		this.transicoes = aux; 
	}
	
	//	Implementa��o dos Getters e Setters
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
	public boolean isEstadoFinal() {
		return estadoFinal;
	}
	public void setEstadoFinal(boolean estadoFinal) {
		this.estadoFinal = estadoFinal;
	}
	public boolean isEstadoInicial() {
		return estadoInicial;
	}
	public void setEstadoInicial(boolean estadoInicial) {
		this.estadoInicial = estadoInicial;
	}
	public Map<String,String> getTransicoes() {
		return transicoes;
	}
	public void setTransicoes(Map<String,String> transicoes) {
		this.transicoes = transicoes;
	}	
	
	/**
	 * Funcao que imprime o nome do estado e se � final e inicial
	 */
	public void imprimeEstado() {
		System.out.println("Nome: "+nome+"\nInicial? "+estadoInicial+"\nFinal?"+estadoFinal);
	}
}