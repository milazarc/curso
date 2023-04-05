package com.example.domains.contracts.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.core.SpaceCamelCase;
import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayNameGeneration(SpaceCamelCase.class)
class LanguageServiceTest {

	@Autowired
	LanguageRepository daoLanguageRepository;
	
	@Autowired
	LanguageService languageService;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Nested
	class Ok {
		@Test
		void testGetOne() {
			Optional<Actor> actor = actorService.getOne(1);
			Actor actor1 = new Actor();
			assertEquals(1, actor.get().getActorId());
		}

		@Test
		void testAdd() {
			fail("Not yet implemented");
		}

		@Test
		void testModify() {
			fail("Not yet implemented");
		}

		@Test
		void testDeleteById() {
			fail("Not yet implemented");
		}

	}
	@Nested
	class Ko {
		@Test
		void testAddErroneo() throws DuplicateKeyException, InvalidDataException {
			Actor actor = new Actor(0, "NOMBREPRUEBABBB", "APELLIDOPRUEBAaaaaaaa");
			
			
			assertThrows(InvalidDataException.class, () -> actorService.add(actor));
			
		}
	}


}
