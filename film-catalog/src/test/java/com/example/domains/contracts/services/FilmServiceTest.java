package com.example.domains.contracts.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.FilmRepository;

@Disabled
@DataJpaTest
@ComponentScan(basePackages = "com.example")
class FilmServiceTest {

	@Autowired
	FilmRepository daoFilmRepository;
	
	@Autowired
	FilmService filmService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testRecientes() {

		System.out.println(filmService.recientes((short) 2000));
		System.out.println("llamando recientes");
		
	}

}
