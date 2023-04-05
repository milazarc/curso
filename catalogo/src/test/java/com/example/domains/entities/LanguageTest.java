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
class LanguageTest {

	Language language;
	
	List<Film> films;
	List<Film> filmsVO;
	
	@BeforeEach
	void setUp() throws Exception {
		language = new Language(1, "Español");
		films = new ArrayList<Film>();
		filmsVO = new ArrayList<Film>();
		Film filmOne = new Film(1);
		filmOne.setTitle("Pelicula 1");
		language.addFilm(filmOne);
		Film filmTwo = new Film(2);
		filmOne.setTitle("Pelicula 2");
		language.addFilmVO(filmTwo);
		films.add(filmOne);
		filmsVO.add(filmTwo);
	}

	@Test
	void testGetLanguageId() {
		assertEquals(1, language.getLanguageId());
	}

	@Test
	void testSetLanguageId() {
		language.setLanguageId(2);
		assertEquals(2, language.getLanguageId());
	}

	@Test
	void testGetName() {
		assertEquals("Español", language.getName());
	}

	@Test
	void testSetName() {
		language.setName("Italiano");
		assertEquals("Italiano", language.getName());
	}

	@Test
	void testGetFilms() {
		assertEquals(films, language.getFilms());
	}

	@Test
	void testSetFilms() {
		Film filmThree = new Film(3);
		filmThree.setTitle("Pelicula 3");
		films.add(filmThree);
		language.setFilms(films);
		assertEquals(films, language.getFilms());
	}

	@Test
	void testClearFilms() {
		language.clearFilms();
		assertEquals(0, language.getFilms().size());
	}

	@Test
	void testAddFilm() {
		Film filmThree = new Film(3);
		filmThree.setTitle("Pelicula 3");
		films.add(filmThree);
		language.addFilm(filmThree);
		assertEquals(films, language.getFilms());
	}

	@Test
	void testRemoveFilm() {
		Film filmOne = new Film(1);
		filmOne.setTitle("Pelicula 1");
		language.removeFilm(filmOne);
		assertEquals(0, language.getFilms().size());
	}

	@Test
	void testGetFilmsVO() {
		assertEquals(filmsVO, language.getFilmsVO());
	}

	@Test
	void testSetFilmsVO() {
		Film filmThree = new Film(3);
		filmThree.setTitle("Pelicula 3");
		filmsVO.add(filmThree);
		language.setFilmsVO(filmsVO);
		assertEquals(filmsVO, language.getFilmsVO());
	}
	
	@Test
	void testClearFilmsVO() {
		language.clearFilmsVO();
		assertEquals(0, language.getFilmsVO().size());
	}

	@Test
	void testAddFilmVO() {
		Film filmThree = new Film(3);
		filmThree.setTitle("Pelicula 3");
		filmsVO.add(filmThree);
		language.addFilmVO(filmThree);
		assertEquals(filmsVO, language.getFilmsVO());
	}

	@Test
	void testRemoveFilmVO() {
		Film filmOne = new Film(2);
		filmOne.setTitle("Pelicula 2");
		language.removeFilmVO(filmOne);
		assertEquals(0, language.getFilmsVO().size());
	}
}
