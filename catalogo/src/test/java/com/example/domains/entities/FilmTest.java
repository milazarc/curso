package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.core.SpaceCamelCase;


@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayNameGeneration(SpaceCamelCase.class)
class FilmTest {
	
	Film film;
	List<Category> filmCategories;
	List<Actor> filmActors;

	@BeforeEach
	void setUp() throws Exception {
		film = new Film(1);
		
		film.setTitle("Pelicula 1");
		film.setDescription("Una pelicula de prueba");
		film.setReleaseYear((short)2000);
		film.setLanguage(new Language(1));
		film.setLanguageVO(new Language(2));
		
		filmCategories = new ArrayList<Category>();
		filmActors = new ArrayList<Actor>();
		
		//Categorias
		Category category1 = new Category(1);
		filmCategories.add(category1);
		film.addCategory(category1);
		Category category2 = new Category(2);
		filmCategories.add(category2);
		film.addCategory(category2);
		
		//Actores
		Actor actor1 = new Actor(1, "NOMBREPRUEBA", "APELLIDOPRUEBA");
		filmActors.add(actor1);
		film.addActor(actor1);
		Actor actor2 = new Actor(2, "NOMBRE", "APELLIDO");
		filmActors.add(actor2);
		film.addActor(actor2);
	}

	@Test
	void testGetFilmId() {
		assertEquals(1, film.getFilmId());
	}

	@Test
	void testSetFilmId() {
		film.setFilmId(2);
		assertEquals(2, film.getFilmId());
	}

	@Test
	void testGetDescription() {
		assertEquals("Una pelicula de prueba", film.getDescription());
	}

	@Test
	void testSetDescription() {
		film.setDescription("Una descripcion de prueba");
		assertEquals("Una descripcion de prueba", film.getDescription());
	}

	@Test
	void testGetReleaseYear() {
		assertEquals((short)2000, film.getReleaseYear());
	}

	@Test
	void testSetReleaseYear() {
		film.setReleaseYear((short)2001);
		assertEquals((short)2001, film.getReleaseYear());
	}

	@Test
	void testGetTitle() {
		assertEquals("Pelicula 1", film.getTitle());
	}

	@Test
	void testSetTitle() {
		film.setTitle("Pelicula 2");
		assertEquals("Pelicula 2", film.getTitle());
	}

	@Test
	void testGetLanguage() {
		assertEquals(new Language(1), film.getLanguage());
	}

	@Test
	void testSetLanguage() {
		Language language = new Language(2);
		film.setLanguage(language);
		assertTrue(film.getLanguage().equals(language));
	}

	@Test
	void testGetLanguageVO() {
		assertEquals(new Language(2), film.getLanguageVO());
	}

	@Test
	void testSetLanguageVO() {
		Language languageVO = new Language(1);
		film.setLanguageVO(languageVO);
		assertTrue(film.getLanguageVO().equals(languageVO));
	}

	@Test
	void testGetActors() {
		assertEquals(filmActors, film.getActors());
	}

	@Test
	void testSetActors() {
		Actor actor3 = new Actor(3, "NOMBREPRUEBA", "APELLIDOPRUEBA");
		filmActors.add(actor3);
		film.setActors(filmActors);
		assertEquals(filmActors, film.getActors());
	}

	@Test
	void testClearActors() {
		film.clearActors();
		assertEquals(0, film.getActors().size());
	}

	@Test
	void testAddActor() {
		Actor actor3 = new Actor(3, "NOMBREPRUEBA", "APELLIDOPRUEBA");
		filmActors.add(actor3);
		film.addActor(actor3);
		assertEquals(filmActors, film.getActors());
	}

	@Test
	void testRemoveActor() {
		Actor actor1 = new Actor(1, "NOMBREPRUEBA", "APELLIDOPRUEBA");
		filmActors.remove(actor1);
		film.removeActor(actor1);
		assertEquals(1, film.getActors().size());
	}

	@Test
	void testGetCategories() {
		assertEquals(filmCategories, film.getCategories());
	}

	@Test
	void testSetCategories() {
		Category category3 = new Category(3);
		filmCategories.add(category3);
		film.setCategories(filmCategories);
		assertEquals(filmCategories, film.getCategories());
	}

	@Test
	void testClearCategories() {
		film.clearCategories();
		assertEquals(0, film.getCategories().size());
	}

	@Test
	void testAddCategoryCategory() {
		Category category3 = new Category(3);
		filmCategories.add(category3);
		film.addCategory(category3);
		assertEquals(filmCategories, film.getCategories());
	}

	@Test
	void testRemoveCategory() {
		Category category1 = new Category(1);
		filmActors.remove(category1);
		film.removeCategory(category1);
		assertEquals(1, film.getCategories().size());
	}

	@Test
	void testEqualsObject() {
		Film film2 = new Film(1);
		assertTrue(film.equals(film2));
	}

}
