package minimizacao;

import java.util.ArrayList;

public class Par {
	//	Adiciona dois objetos estados
	Estado par1;
	Estado par2;
	//	Array com a lista de pares que dependem deste par para serem iguais
	ArrayList<Par> paresDependentes = new ArrayList<Par>();
	//	Indica se o par é igual ou não: false -> NÃO É IGUAL; true -> É IGUAL
	boolean parFinal;
	//	Indica o motivo do par não ser igual
	String motivo;
	
	//	Construtor do objeto tipo Par
	public Par(Estado par1, Estado par2, ArrayList<Par> paresDependentes, boolean parFinal, String motivo) {
		super();
		this.par1 = par1;
		this.par2 = par2;
		this.paresDependentes = paresDependentes;
		this.parFinal = parFinal;
		this.motivo = motivo;
	}
	
	//	Implementação dos Getters e Setters
	public Estado getPar1() {
		return par1;
	}
	public void setPar1(Estado par1) {
		this.par1 = par1;
	}
	public Estado getPar2() {
		return par2;
	}
	public void setPar2(Estado par2) {
		this.par2 = par2;
	}
	public ArrayList<Par> getParesDependentes() {
		return paresDependentes;
	}
	public void setParesDependentes(ArrayList<Par> paresDependentes) {
		this.paresDependentes = paresDependentes;
	}
	public boolean isParFinal() {
		return parFinal;
	}
	public void setParFinal(boolean parFinal) {
		this.parFinal = parFinal;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
	
}
