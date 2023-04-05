package com.example.domains.contracts.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.core.SpaceCamelCase;
import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;


@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayNameGeneration(SpaceCamelCase.class)
class FilmServiceTest {
	
	@Autowired
	FilmRepository daoFilmRepository;
	
	@Autowired
	FilmService filmService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetOne() {
		Film film = filmService.getOne(1).get();
		assertEquals(1, film.getFilmId());
		assertEquals("ACADEMY DINOSAUR", film.getTitle());
	}

	@Test
	void testAdd() {
		Film film = new Film(0);
		
	}

	@Test
	void testModify() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteById() {
		
	}

}
