package com.example.demo;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.contracts.services.CategoryService;


@SpringBootTest
class CatalogoApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	CategoryRepository daoCategoryRepository;
	
	@Autowired
	CategoryService categoryService;

	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {

		categoryService.funcionPrueba().forEach(System.out::println);
		System.out.println("-------------------------");
		daoCategoryRepository.findAll().forEach(System.out::println);
		System.out.println("-------------------------");
		System.out.println(categoryService.funcionPrueba());
		
	}

}
