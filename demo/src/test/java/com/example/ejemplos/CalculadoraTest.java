package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.core.test.Smoke;
import com.example.core.test.SpaceCamelCase;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class CalculadoraTest {

	Calculadora calc;
	
	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}

	@Nested
	@DisplayName("Pruebas del metodo Suma")
	@DisplayNameGeneration(SpaceCamelCase.class)
	class Suma {
		@Nested
		class Ok {
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

			@Smoke
			@Test
			void testSumaDecimales() {
				
				var rslt = calc.suma(0.1, 0.2);
				
				assertEquals(0.3, rslt);
			}
			
			@ParameterizedTest(name = "{0} + {1} = {2}")
			@CsvSource(value = {"1, 1, 2", "0.1, 0.2, 0.3", "-1,-3,-4"})
			void testSumaMultipleParametrizada(double op1, double op2, double rslt) {
			
				assertEquals(rslt, calc.suma(op1, op2));
			}
			



		}
		@Nested
		class Ko {
			
		}
	}
	
	@Nested
	@DisplayName("Pruebas del metodo Divide")
	class Divide {
		@Nested
		class Ok {

			void testDividirPorPositivo() {
				assertThrows(ArithmeticException.class, () -> calc.divide(1, 1));
			}
			
			void testDividirPorCero() {
				assertThrows(ArithmeticException.class, () -> calc.divide(1, 0));
			}
		}
		
		@Nested
		class Ko {
			@Test
			void testDividirPorCero() {
				assertThrows(ArithmeticException.class, () -> calc.divide(1, 0.0));
			}
			
		}
	}
	

	

}
