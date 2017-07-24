package minimizacao;

import static org.junit.Assert.*;

import org.junit.Test;

public class EstadoTeste {

	@Test
	public void testEstado() {
		Estado e1 = new Estado("q0",false,true);
		assertEquals("q0", e1.getNome());
		assertEquals(false, e1.isEstadoFinal());
		assertEquals(true, e1.isEstadoInicial());
		assertEquals(null, e1.getTransicoes());
	}

	@Test
	public void testGetNome() {
		Estado e1 = new Estado("q0",false,true);
		assertEquals("q0", e1.getNome());
	}

	@Test
	public void testSetNome() {
		Estado e1 = new Estado("q0",false,true);
		e1.setNome("OLAR");
		assertEquals("OLAR", e1.getNome());
	}

	@Test
	public void testIsEstadoFinal() {
		Estado e1 = new Estado("q0",false,true);
		assertEquals(false, e1.isEstadoFinal());
	}

	@Test
	public void testSetEstadoFinal() {
		Estado e1 = new Estado("q0",false,true);
		e1.setEstadoFinal(true);
		assertEquals(true, e1.isEstadoFinal());
	}

	@Test
	public void testIsEstadoInicial() {
		Estado e1 = new Estado("q0",false,true);
		assertEquals(true, e1.isEstadoInicial());
	}

	@Test
	public void testSetEstadoInicial() {
		Estado e1 = new Estado("q0",false,true);
		e1.setEstadoInicial(false);
		assertEquals(false,e1.isEstadoInicial());
	}

	@Test
	public void testGetTransicoes() {
		Estado e1 = new Estado("q0",false,true);
		e1.transicoes.put("a", "q0");
		assertEquals("q0", e1.getTransicoes().get("a"));
	}

	@Test
	public void testSetTransicoes() {
		Estado e1 = new Estado("q0",false,true);
		e1.setTransicoes(null);
		assertEquals(null, e1.getTransicoes());
	}

}
