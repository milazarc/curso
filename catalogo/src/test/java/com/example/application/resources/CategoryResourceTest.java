package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.dtos.CategoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

class CategoryResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CategoryService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAllString() {
		List<CategoryDTO> listaCategoryDTOs = new ArrayList<>(
				Arrays.asList(new CategoryDTO(1, "Peli")))
	}

	@Test
	void testGetAllPageable() {
		fail("Not yet implemented");
	}

	@Test
	void testGetOne() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPelis() {
		fail("Not yet implemented");
	}

	@Test
	void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}
