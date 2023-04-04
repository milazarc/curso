package com.example.domains.contracts.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;



@DataJpaTest
@ComponentScan(basePackages = "com.example")
class CategoryRepositoryTest {

	@Autowired
	CategoryRepository daoCategoryRepository;
	
	@Autowired
	CategoryService categoryService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
		
		daoCategoryRepository.findAll();
	}

}
