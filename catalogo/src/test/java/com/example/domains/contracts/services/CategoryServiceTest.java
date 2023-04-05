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
import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayNameGeneration(SpaceCamelCase.class)
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
		Category category = categoryService.getOne(1).get();
		assertEquals(1, category.getCategoryId());
		assertEquals("Action", category.getName());
	}

	@Test
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		Category category = new Category(0);
		category.setName("prueba");
		Category category2 = categoryService.add(category);
		assertEquals("prueba", categoryService.getOne(category2.getCategoryId()).get().getName());
		categoryService.deleteById(category2.getCategoryId());
	}

	@Test
	void testModify() throws NotFoundException, InvalidDataException, DuplicateKeyException {
		Category category = new Category(0);
		category.setName("prueba");
		Category category2 = categoryService.add(category);
		category2.setName("pnuesafsrueba");
		categoryService.modify(category);
		assertEquals("pnuesafsrueba", category2.getName());
	}

	@Test
	void testDeleteById() throws DuplicateKeyException, InvalidDataException {
		Category category = new Category(0);
		category.setName("prueba");
		Category category2 = categoryService.add(category);
		categoryService.deleteById(category2.getCategoryId());
		assertTrue(categoryService.getOne(category2.getCategoryId()).isEmpty());
	}

}
