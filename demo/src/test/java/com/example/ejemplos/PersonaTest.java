package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aspectj.weaver.ast.Var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.support.DaoSupport;

import com.example.core.test.SpaceCamelCase;
import com.example.exceptions.InvalidDataException;
import com.example.ioc.PersonaRepository;

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
		class OK {
			@Test
			void testCreate() {
				var p = Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build();

				assertNotNull(p);
				assertTrue(p instanceof Persona, "No es una instancia de persona");

				assertAll("Validar propiedades", () -> assertEquals(1, p.getId(), "id"),
						() -> assertEquals("Pepito", p.getNombre(), "getNombre"),
						() -> assertEquals("Grillo", p.getApellidos().get(), "getApellidos"));

			}

			@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
			@DisplayName("Test multiples creaciones")
			void repeatedTestCreate(RepetitionInfo repetitionInfo) {
				var p = Persona.builder().id(repetitionInfo.getCurrentRepetition())
						.nombre("Pepito" + repetitionInfo.getCurrentRepetition()).apellidos("Grillo").build();

				assertNotNull(p);
				assertTrue(p instanceof Persona, "No es una instancia de persona");

				assertAll("Validar propiedades",
						() -> assertEquals(repetitionInfo.getCurrentRepetition(), p.getId(), "id"),
						() -> assertEquals("Pepito" + repetitionInfo.getCurrentRepetition(), p.getNombre(),
								"getNombre"),
						() -> assertEquals("Grillo", p.getApellidos().get(), "getApellidos"));

			}

			@Nested
			class PersonaRepositoryTest {
				@Mock
				PersonaRepository dao;

				@BeforeEach
				void setUp() throws InvalidDataException {
					dao = mock(PersonaRepository.class);
					when(dao.load()).thenReturn(Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build());
					doThrow(new IllegalArgumentException()).when(dao).save(null);
				}

				@Test
				void testLoad() {
					
					// ...
					
				var p = dao.load();
				
				
				assertTrue(p instanceof Persona, "No es una instancia de persona");
				
				assertAll("Validar propiedades", 
						() -> assertEquals(1, p.getId(), "id"),
						() -> assertEquals("Pepito", p.getNombre(), "getNombre"),
						() -> assertEquals("Grillo", p.getApellidos().get(), "getApellidos"));
				
				}
				
				@Test
				void testSave(){
					
					// ...

					assertThrows(IllegalArgumentException.class, () -> dao.save(null));

				}

			}

		}

		@Nested
		@DisplayName("KO")
		class KO {

		}
	}

}
