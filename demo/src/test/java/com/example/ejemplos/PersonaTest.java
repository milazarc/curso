package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import com.example.core.test.SpaceCamelCase;

class PersonaTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	
	@Nested
	@DisplayName("Pruebas del constructor")
	@DisplayNameGeneration(SpaceCamelCase.class)
	class create {
		@Nested
		@DisplayName("OK")
		class OK{
			@Test
			void testCreate() {
				var p = Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build();
				
				assertNotNull(p);
				assertTrue(p instanceof Persona, "No es una instancia de persona");
				
				assertAll("Validar propiedades", 
						() -> assertEquals(1, p.getId(), "id"),
						() -> assertEquals("Pepito", p.getNombre(), "getNombre"),
						() -> assertEquals("Grillo", p.getApellidos(), "getApellidos"));
				
				
			}
			
			@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
			@DisplayName("Test multiples creaciones")
				void repeatedTestCreate(RepetitionInfo repetitionInfo) {
				var p = Persona.builder().id(repetitionInfo.getCurrentRepetition())
						.nombre("Pepito" + repetitionInfo.getCurrentRepetition() )
						.apellidos("Grillo").build();
				
				assertNotNull(p);
				assertTrue(p instanceof Persona, "No es una instancia de persona");
				
				assertAll("Validar propiedades", 
						() -> assertEquals(repetitionInfo.getCurrentRepetition(), p.getId(), "id"),
						() -> assertEquals("Pepito" + repetitionInfo.getCurrentRepetition(), p.getNombre(), "getNombre"),
						() -> assertEquals("Grillo", p.getApellidos(), "getApellidos"));
				
			}
			
		}
		
		@Nested
		@DisplayName("KO")
		class KO{
			
		}
	}


	


}
