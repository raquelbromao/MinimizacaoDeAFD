package minimizacao;

public class Transicao {
	//	Estado que parte a transicao
	Estado inicio;
	//	Simbolo que deverá ser lido
	String simbolo;
	//	Estado que é atingido com a transicao
	Estado fim;
	
	/**
	 * Construtor do objeto tipo Transicao
	 * @param inicio
	 * @param simbolo
	 * @param fim
	 */
	public Transicao(Estado inicio, String simbolo, Estado fim) {
		super();
		this.inicio = inicio;
		this.simbolo = simbolo;
		this.fim = fim;
	}
	//	Implementação dos Getters e Setters
	public Estado getInicio() {
		return inicio;
	}
	public void setInicio(Estado inicio) {
		this.inicio = inicio;
	}
	public String getSimbolo() {
		return simbolo;
	}
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	public Estado getFim() {
		return fim;
	}
	public void setFim(Estado fim) {
		this.fim = fim;
	}
	
	
			
}
