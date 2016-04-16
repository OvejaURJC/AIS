package test;

import ais.CalculatorData;
import ais.DivisionByZeroException;
import ais.SyntaxException;
import junit.framework.TestCase;

public class Pruebas extends TestCase {

	CalculatorData calc;
	
	protected void setUp() {
		calc = new CalculatorData();
	}
	
	// Clases validas
	public void testUno() {
		try {
			double result = calc.execute("3+(-2)");
			assertTrue(result == 1);
		} catch (Exception e) {
			fail();
		}
	}
	
	public void testDos() {
		try {
			double result = calc.execute("(4+2)/3");
			assertTrue(result == 2);
		} catch (Exception e) {
			fail();
		}
	}
	
	public void testTres() {
		try {
			double result = calc.execute("(27-7)*0.5");
			assertTrue(result == 10);
		} catch (Exception e) {
			fail();
		}
	}
	
	public void testCuatro() {
		try {
			double result = calc.execute("9/3");
			assertTrue(result == 3);
		} catch (Exception e) {
			fail();
		}
	}
	
	// Clases inválidas
	public void testCinco() {
		try {
			calc.execute("3+-2");
			fail();
		} catch (Exception e) {
			assertTrue(e.getClass().equals(new SyntaxException("error").getClass()));
		}
	}
	
	public void testSeis() {
		try {
			calc.execute("-1*0");
			fail();
		} catch (Exception e) {
			assertTrue(e.getClass().equals(new SyntaxException("error").getClass()));
		}
	}
	
	public void testSiete() {
		try {
			calc.execute("(4+*3)/3");
			fail();
		} catch (Exception e) {
			assertTrue(e.getClass().equals(new SyntaxException("error").getClass()));
		}
	}
	
	public void testOcho() {
		try {
			calc.execute("72/");
			fail();
		} catch (Exception e) {
			assertTrue(e.getClass().equals(new SyntaxException("error").getClass()));
		}
	}
	
	public void testNueve() {
		try {
			calc.execute("27-7)*0.5");
			fail();
		} catch (Exception e) {
			assertTrue(e.getClass().equals(new SyntaxException("error").getClass()));
		}
	}
	
	public void testDiez() {
		try {
			calc.execute("(27-7*0.5");
			fail();
		} catch (Exception e) {
			assertTrue(e.getClass().equals(new SyntaxException("error").getClass()));
		}
	}
	
	public void testOnce() {
		try {
			calc.execute("9/0");
			fail();
		} catch (Exception e) {
			assertTrue(e.getClass().equals(new DivisionByZeroException("error").getClass()));
		}
	}
	
	public void testDoce() {
		try {
			calc.execute("(8-8)/0");
			fail();
		} catch (Exception e) {
			assertTrue(e.getClass().equals(new DivisionByZeroException("error").getClass()));
		}
	}
	
}