package minimizacao;

import static org.junit.Assert.*;

import org.junit.Test;

public class AutomatoTeste {

	@Test
	public void testAutomato() {
		Estado q0 = new Estado("q0", true, false);
		Automato aut1 = new Automato(null,null,q0,null);
		assertEquals(null,aut1.getEstados());
		assertEquals(null, aut1.getAlfabeto());
		assertEquals(q0,aut1.getEstadoInicial());
		assertEquals(null,aut1.getEstadosFinais());
	}

	@Test
	public void testGetEstados() {
		Estado q0 = new Estado("q0", true, false);
		Automato aut1 = new Automato(null,null,q0,null);
		assertEquals(null,aut1.getEstados());
	}

	@Test
	public void testSetEstados() {
		Estado q0 = new Estado("q0", true, false);
		Automato aut1 = new Automato(null,null,q0,null);
		aut1.setEstados(null);
		assertEquals(null,aut1.getEstados());
	}

	@Test
	public void testGetAlfabeto() {
		Estado q0 = new Estado("q0", true, false);
		Automato aut1 = new Automato(null,null,q0,null);
		assertEquals(null,aut1.getAlfabeto());
	}

	@Test
	public void testSetAlfabeto() {
		Estado q0 = new Estado("q0", true, false);
		Automato aut1 = new Automato(null,null,q0,null);
		aut1.setAlfabeto(null);
		assertEquals(null,aut1.getAlfabeto());
	}

	@Test
	public void testGetEstadoInicial() {
		Estado q0 = new Estado("q0", true, false);
		Automato aut1 = new Automato(null,null,q0,null);
		assertEquals(q0,aut1.getEstadoInicial());
	}

	@Test
	public void testSetEstadoInicial() {
		Estado q0 = new Estado("q0", true, false);
		Automato aut1 = new Automato(null,null,q0,null);
		aut1.setEstadoInicial(null);
		assertEquals(null,aut1.getEstadoInicial());
	}

	@Test
	public void testGetEstadosFinais() {
		Estado q0 = new Estado("q0", true, false);
		Automato aut1 = new Automato(null,null,q0,null);
		assertEquals(null,aut1.getEstadosFinais());
	}

	@Test
	public void testSetEstadosFinais() {
		Estado q0 = new Estado("q0", true, false);
		Automato aut1 = new Automato(null,null,q0,null);
		aut1.setEstadosFinais(null);
		assertEquals(null,aut1.getEstadosFinais());
	}
}
