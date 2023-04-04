package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.core.SpaceCamelCase;


@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayNameGeneration(SpaceCamelCase.class)
class ActorTest {

	Actor actor;
	
	List<Film> filmsActors = new ArrayList<Film>();
	
	@BeforeEach
	void setUp() throws Exception {
		actor = new Actor(300, "NOMBREPRUEBA", "APELLIDOPRUEBA");
		Film filmOne = new Film(1);
		filmOne.setTitle("Pelicula 1");
		actor.addFilm(filmOne);
		Film filmTwo = new Film(2);
		filmOne.setTitle("Pelicula 2");
		actor.addFilm(filmTwo);
		filmsActors.add(filmOne);
		filmsActors.add(filmTwo);
	}
	
	@Nested
	class Ok{
		
		@Test
		void testGetActorId() {
			assertEquals(300, actor.getActorId());
		}

		@Test
		void testSetActorId() {
			actor.setActorId(301);
			assertEquals(301, actor.getActorId());
		}

		@Test
		void testGetFirstName() {
			assertEquals("NOMBREPRUEBA", actor.getFirstName());
		}

		@Test
		void testSetFirstName() {
			actor.setFirstName("NOMBREPRUEBAAAA");
			assertEquals("NOMBREPRUEBAAAA", actor.getFirstName());
		}

		@Test
		void testGetLastName() {
			assertEquals("APELLIDOPRUEBA", actor.getLastName());
		}

		@Test
		void testSetLastName() {
			actor.setLastName("APELLIDOPRUEBAAAAAAAA");
			assertEquals("APELLIDOPRUEBAAAAAAAA", actor.getLastName());
		}

		@Test
		void testGetFilms() {
			assertEquals(filmsActors, actor.getFilms());
		}

		@Test
		void testSetFilms() {
			Film filmThree = new Film(3);
			filmThree.setTitle("Pelicula 3");
			filmsActors.add(filmThree);
			actor.setFilms(filmsActors);
			assertEquals(filmsActors, actor.getFilms());
		}

		@Test
		void testClearFilms() {
			actor.clearFilms();
			assertEquals(0, actor.getFilms().size());
		}

		@Test
		void testAddFilm() {
			Film filmThree = new Film(3);
			filmThree.setTitle("Pelicula 3");
			actor.addFilm(filmThree);
			filmsActors.add(filmThree);
			assertEquals(filmsActors, actor.getFilms());
		}

		@Test
		void testRemoveFilm() {
			Film filmOne = new Film(1);
			filmOne.setTitle("Pelicula 1");
			actor.removeFilm(filmOne);
			assertEquals(1, actor.getFilms().size());
		}

		@Test
		void testEqualsObject() {
			Actor actorNuevo = new Actor(300, "NOMBREPRUEBA", "APELLIDOPRUEBA");
			assertTrue(actor.equals(actorNuevo));
		}
	}

	

}
