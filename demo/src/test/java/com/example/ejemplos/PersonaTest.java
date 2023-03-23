package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreate() {
		var p = Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build();
		
		assertNotNull(p);
		assertTrue(p instanceof Persona, "No es una instancia de persona");
		assertEquals(1, p.getId());
		assertEquals("Pepito", p.getNombre());
		assertEquals("Grillo", p.getApellidos());
	}

}
