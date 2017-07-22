package minimizacao;

import java.util.ArrayList;
import java.util.List;

public class Automato {
	//	Lista de estados do automato.
	private ArrayList<Estado> estados = new ArrayList<Estado>();;
	//	Alfabeto do automato
	private ArrayList<Character> alfabeto = new ArrayList<Character>();
	//	Estado inicial do automato
	private Estado estadoInicial;
	// 	Estados finais do automato
	private ArrayList<Estado> estadosFinais = new ArrayList<Estado>();
	
	/**
	 * Construtor da classe
	 * 
	 * @param estados lista de todos os estados do AFD
	 * @param alfabeto lista contendo o algabeto do AFD
	 * @param estadoInicial estado inicial do AFD
	 * @param estadosFinais lista contendo os estados finais do AFD
	 */
	public Automato(ArrayList<Estado> estados, ArrayList<Character> alfabeto, Estado estadoInicial,
			ArrayList<Estado> estadosFinais) {
		super();
		this.estados = estados;
		this.alfabeto = alfabeto;
		this.estadoInicial = estadoInicial;
		this.estadosFinais = estadosFinais;
	}
	
	public ArrayList<Estado> getEstados() {
		return estados;
	}

	public void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}

	public ArrayList<Character> getAlfabeto() {
		return alfabeto;
	}

	public void setAlfabeto(ArrayList<Character> alfabeto) {
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

	/**
	 * Adiciona um estado ao automato.
	 * @param estado estado que será inserido no automato.
	 */
	public void addEstado(Estado estado) {
		this.estados.add(estado);
	}
		
	/**
	 * Indica quais estados são finais.
	 * @param estado lista de nomes dos estados finais.
	 */
	/*public void setEstadoFinal(List<String> estados) {
		for (Estado estado : this.estados) {
			if (estados.contains(estado.getNome())) {
				estados.
			}
		}
	}
		
	/**
	 * Indica qual o estado que será usado como inicial.
	 * @param estado nome do estado inicial.
	 */
	/*public void setEstadoInicial(String estado) {
		for (Estado e : this.estados) {
			if (e.getNome().equals(estado)) {
				this.estadoInicial = e;
				this.estadoInicial.setInicial();
				break;
			}
		}
	}*/
}