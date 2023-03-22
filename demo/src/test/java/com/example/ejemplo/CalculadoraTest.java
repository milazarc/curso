package com.example.ejemplo;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CalculadoraTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testDivPositivoPositivo() {
		var calc = new Calculadora();
		
		var rsult = calc.divide(2, 2);
		
		assertEquals(1, rsult);
	}
	
	
	@Test
	void testSumaPositivoPositivo() {
		var calc = new Calculadora();
		
		var rsult = calc.suma(2, 2);
		
		assertEquals(4, rsult);
	}
	
	@Test
	void testSumaPositivoNegativo() {
		var calc = new Calculadora();
		
		var rsult = calc.suma(2, -2);
		
		assertEquals(0, rsult);
	}
	
	@Test
	void testSumaNegativoPositivo() {
		var calc = new Calculadora();
		
		var rsult = calc.suma(-2, 2);
		
		assertEquals(0, rsult);
	}
	
	@Test
	void testSumaNegativoNegativo() {
		var calc = new Calculadora();
		
		var rsult = calc.suma(-2, -2);
		
		assertEquals(-4, rsult);
	}
	
	@Test
	void testSumaPositivoPositivoDecimales() {
		var calc = new Calculadora();
		
		var rsult = calc.suma(2.1, 2.3);
		
		assertEquals(4.4, rsult);
	}
	
	@Test
	void testSumaPositivoNegativoDecimales() {
		var calc = new Calculadora();
		
		var rsult = calc.suma(2.4, -2.1);
		
		assertEquals(0.3, rsult);
	}
	
	@Test
	void testSumaNegativoPositivoDecimales() {
		var calc = new Calculadora();
		
		var rsult = calc.suma(-2.4, 2.3);
		
		assertEquals(0.1, rsult);
	}
	
	@Test
	void testSumaNegativoNegativoDecimales() {
		var calc = new Calculadora();
		
		var rsult = calc.suma(-2.4, -2.3);
		
		assertEquals(-4.7, rsult);
	}

}
