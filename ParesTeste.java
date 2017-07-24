package minimizacao;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParesTeste {
	Estado e1 = new Estado("q1", false, false);
	Estado e2 = new Estado("q0", true, false);
	
	@Test
	public void testPar() {
		Par p1 = new Par(e1, e2, null, true, 0, null);
		assertEquals(e1, p1.getPar1());
		assertEquals(e2, p1.getPar2());
		assertEquals(null, p1.getParesDependentes());
		assertEquals(true, p1.isParFinal());
		assertEquals(new Integer(0), p1.getParFinal2());
		assertEquals(null, p1.getMotivo());
	}

	@Test
	public void testGetPar1() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		assertEquals(e1, p1.getPar1());
	}

	@Test
	public void testSetPar1() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		p1.setPar1(e2);
		assertEquals(e2, p1.getPar1());
	}

	@Test
	public void testGetPar2() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		assertEquals(e2, p1.getPar2());
	}

	@Test
	public void testSetPar2() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		p1.setPar2(e1);
		assertEquals(e1, p1.getPar2());
	}

	@Test
	public void testGetParesDependentes() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		assertEquals(null, p1.getParesDependentes());
	}

	@Test
	public void testSetParesDependentes() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		p1.setParesDependentes(null);
		assertEquals(null, p1.getParesDependentes());
	}

	@Test
	public void testIsParFinal() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		assertEquals(true, p1.isParFinal());
	}

	@Test
	public void testSetParFinal() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		p1.setParFinal(false);
		assertEquals(false, p1.isParFinal());
	}

	@Test
	public void testGetParFinal2() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		assertEquals(new Integer(0), p1.getParFinal2());
	}

	@Test
	public void testSetParFinal2() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		p1.setParFinal2(1);
		assertEquals(new Integer(1), p1.getParFinal2());
	}

	@Test
	public void testGetMotivo() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		assertEquals(null, p1.getMotivo());
	}

	@Test
	public void testSetMotivo() {
		Par p1 = new Par(e1, e2, null, true, 0, null );
		p1.setMotivo("OLAR");
		assertEquals("OLAR", p1.getMotivo());
	}

}
