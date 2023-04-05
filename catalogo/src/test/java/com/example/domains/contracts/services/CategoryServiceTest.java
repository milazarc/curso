package com.example.domains.contracts.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.domains.contracts.repositories.CategoryRepository;

class CategoryServiceTest {

	@Autowired
	CategoryRepository daoCategoryRepository;
	
	@Autowired
	CategoryService categoryService;
	
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetOne() {
		fail("Not yet implemented");
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
