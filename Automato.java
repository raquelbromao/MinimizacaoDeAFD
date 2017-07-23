package minimizacao;

import java.util.ArrayList;
import java.util.List;

public class Automato {
	//	Lista de estados do automato.
	private ArrayList<Estado> estados = new ArrayList<Estado>();;
	//	Alfabeto do automato
	private ArrayList<String> alfabeto = new ArrayList<String>();
	//	Estado inicial do automato
	private Estado estadoInicial;
	// 	Estados finais do automato
	private ArrayList<Estado> estadosFinais = new ArrayList<Estado>();
	private ArrayList<Transicao> transicoes = new ArrayList<Transicao>();
	
	/**
	 * Construtor do objeto tipo Automato
	 * @param estados
	 * @param alfabeto
	 * @param estadoInicial
	 * @param estadosFinais
	 * @param transicoes
	 */
	public Automato(ArrayList<Estado> estados, ArrayList<String> alfabeto, Estado estadoInicial,
			ArrayList<Estado> estadosFinais, ArrayList<Transicao> transicoes) {
		super();
		this.estados = estados;
		this.alfabeto = alfabeto;
		this.estadoInicial = estadoInicial;
		this.estadosFinais = estadosFinais;
		this.transicoes = transicoes;
	}

	//	Implementação dos Getters e Setters
	public ArrayList<Estado> getEstados() {
		return estados;
	}
	public void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}
	public ArrayList<String> getAlfabeto() {
		return alfabeto;
	}
	public void setAlfabeto(ArrayList<String> alfabeto) {
		this.alfabeto = alfabeto;
	}
	public Estado getEstadoInicial() {
		return estadoInicial;
	}
	public void setEstadoInicial(Estado estadoInicial) {
		this.estadoInicial = estadoInicial;
	}
	public ArrayList<Estado> getEstadosFinais() {
		return estadosFinais;
	}
	public void setEstadosFinais(ArrayList<Estado> estadosFinais) {
		this.estadosFinais = estadosFinais;
	}
	public ArrayList<Transicao> getTransicoes() {
		return transicoes;
	}
	public void setTransicoes(ArrayList<Transicao> transicoes) {
		this.transicoes = transicoes;
	}

	/**
	 * Adiciona um estado ao automato.
	 * @param estado estado que será inserido no automato.
	 */
	public void addEstado(Estado estado) {
		this.estados.add(estado);
	}
	
	/**
	 * Funcao que junta os estado passados em um unico estado e adiciona no AFD
	 * Tambem exclui esses estados individuais do AFD
	 * @param estados ArrayList com os estados que devem se juntar em um so
	 */
	public void criarEstado(ArrayList<Estado> estados) {
		
	}
	
	/*public void adicionaTransicao(char entrada, Estado estado) {
		Transicao t = new Transicao(entrada, estado);
		this.transicoes.add(t);
	}*/
}