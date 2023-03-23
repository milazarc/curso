package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

	Calculadora calc;
	
	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}

	@Nested
	@DisplayName("Pruebas del metodo Suma")
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class Suma {
		@Nested
		class OK {
			@Test
			void testSumaPositivoPositivo() {
				
				var rslt = calc.suma(2, 2);
				
				assertEquals(4, rslt);
			}

			@Test
			void testSumaPositivoNegativo() {
				
				var rslt = calc.suma(1, -0.9);
				
				assertEquals(0.1, rslt);
			}

			@Test
			void testSumaNegativoPositivo() {
				
				var rslt = calc.suma(-1, 5);
				
				assertEquals(4, rslt);
			}

			@Test
			void testSumaNegativoNegativo() {
				
				var rslt = calc.suma(-1, -5);
				
				assertEquals(-6, rslt);
			}

			@Test
			void testSumaDecimales() {
				
				var rslt = calc.suma(0.1, 0.2);
				
				assertEquals(0.3, rslt);
			}
			



		}
		@Nested
		class KO {
			
		}
	}
	
	@Nested
	@DisplayName("Pruebas del metodo Divide")
	class Divide {
		@Nested
		class OK {

			void testDividirPorPositivo() {
				assertThrows(ArithmeticException.class, () -> calc.divide(1, 1));
			}
			
			void testDividirPorCero() {
				assertThrows(ArithmeticException.class, () -> calc.divide(1, 0));
			}
		}
		
		@Nested
		class KO {
			@Test
			void testDividirPorCero() {
				assertThrows(ArithmeticException.class, () -> calc.divide(1, 0.0));
			}
			
		}
	}
	

	

}
