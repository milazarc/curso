package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {

	
	Category category;
	List<Film> films;
	
	@BeforeEach
	void setUp() throws Exception {
		category = new Category(1);
		category.setName("prueba");
		films = new ArrayList<Film>();
		Film filmOne = new Film(1);
		filmOne.setTitle("Pelicula 1");
		category.addFilm(filmOne);
		Film filmTwo = new Film(2);
		filmOne.setTitle("Pelicula 2");
		category.addFilm(filmTwo);
		films.add(filmOne);
		films.add(filmTwo);
	}

	@Test
	void testGetCategoryId() {
		assertEquals(1, category.getCategoryId());
	}

	@Test
	void testSetCategoryId() {
		category.setCategoryId(2);
		assertEquals(2, category.getCategoryId());
	}

	@Test
	void testGetName() {
		assertEquals("prueba", category.getName());
	}

	@Test
	void testSetName() {
		category.setName("nombrenuevo");
		assertEquals("nombrenuevo", category.getName());
	}

	@Test
	void testGetFilms() {
		assertEquals(films, category.getFilms());
	}

	@Test
	void testSetFilms() {
		Film filmThree = new Film(3);
		filmThree.setTitle("Pelicula 3");
		films.add(filmThree);
		category.setFilms(films);
		assertEquals(films, category.getFilms());
	}

	@Test
	void testClearFilms() {
		category.clearFilms();
		assertEquals(0, category.getFilms().size());
	}

	@Test
	void testAddFilm() {
		Film filmThree = new Film(3);
		filmThree.setTitle("Pelicula 3");
		films.add(filmThree);
		category.addFilm(filmThree);
		assertEquals(films, category.getFilms());
	}

	@Test
	void testRemoveFilm() {
		Film filmOne = new Film(1);
		filmOne.setTitle("Pelicula 1");
		category.removeFilm(filmOne);
		assertEquals(1, category.getFilms().size());
	}

	@Test
	void testEqualsObject() {
		Category category2 = new Category(1);
		category2.setName("prueba");
		assertTrue(category.equals(category2));
	}

}
