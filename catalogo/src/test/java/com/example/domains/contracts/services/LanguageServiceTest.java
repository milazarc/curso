package com.example.domains.contracts.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
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
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayNameGeneration(SpaceCamelCase.class)
class LanguageServiceTest {

	@Autowired
	LanguageRepository daoLanguageRepository;
	
	@Autowired
	LanguageService languageService;
	
	@Autowired
	FilmService filmService;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Nested
	class Ok {
		@Test
		void testGetOne() {
			Language language = languageService.getOne(1).get();
			assertEquals(1, language.getLanguageId());
			assertEquals("English", language.getName());
		}

		@Test
		void testAdd() throws DuplicateKeyException, InvalidDataException {
			Language language = new Language(0, "Romanian");
			Language language2 = languageService.add(language);
			assertEquals("Romanian", languageService.getOne(language2.getLanguageId()).get().getName());
			languageService.deleteById(language2.getLanguageId());
		}

		@Test
		void testModify() throws NotFoundException, InvalidDataException, DuplicateKeyException {
			Language language = new Language(0, "Romanian");
			Language language2 = languageService.add(language);
			language2.setName("Other");
			languageService.modify(language2);
			assertEquals("Other", languageService.getOne(language2.getLanguageId()).get().getName());
			languageService.deleteById(language2.getLanguageId());
		
		}

		@Test
		void testDeleteById() throws DuplicateKeyException, InvalidDataException {
			Language language = new Language(0, "Romanian");
			Language language2 = languageService.add(language);
			languageService.deleteById(language2.getLanguageId());
			assertTrue(languageService.getOne(language2.getLanguageId()).isEmpty());
			
		}

	}

}
