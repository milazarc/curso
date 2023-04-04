package com.example.demo;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Language;

import jakarta.transaction.Transactional;


@SpringBootTest
class CatalogoApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	CategoryRepository daoCategoryRepository;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	FilmRepository daoFilmRepository;
	
	@Autowired
	LanguageRepository languageRepository;

	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@Transactional
	void test() {

		categoryService.funcionPrueba().forEach(System.out::println);
		System.out.println("-------------------------");
		daoCategoryRepository.findAll().forEach(System.out::println);
		System.out.println("-------------------------");
		System.out.println(categoryService.funcionPrueba());
		System.out.println("-------------------------");
		daoFilmRepository.findAll().forEach(System.out::println);
		System.out.println("-------------------------");
		languageRepository.findAll().forEach(System.out::println);
		
	}

}
