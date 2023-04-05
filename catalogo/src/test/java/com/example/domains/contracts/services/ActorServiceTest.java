package com.example.domains.contracts.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import javax.swing.plaf.ActionMapUIResource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDatabaseInitializerDetector;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;

import com.example.core.SpaceCamelCase;
import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayNameGeneration(SpaceCamelCase.class)
class ActorServiceTest {

	@Autowired
	ActorRepository daoActorRepository;
	
	@Autowired
	ActorService actorService;
	
	
	@BeforeEach
	void setUp() throws Exception {
		
	}



	@Nested
	class Ok {


		@Test
		void testGetOne() {
			Optional<Actor> actor = actorService.getOne(1);
			Actor actor1 = new Actor();
			assertEquals(1, actor.get().getActorId());
		}

		@Test
		@Transactional
		void testAdd() throws DuplicateKeyException, InvalidDataException {
			daoActorRepository.deleteByFirstName("NOMBREPRUEBAA");
			daoActorRepository.deleteByFirstName("NOMBREPRUEBA");
			Actor actor = new Actor(0, "NOMBREPRUEBAA", "APELLIDOPRUEBA");
			actorService.add(actor);
			
			Actor actor2 = daoActorRepository.findByFirstName("NOMBREPRUEBAA");
			assertEquals("NOMBREPRUEBAA", actor2.getFirstName());
			
		}

		@Test
		@Transactional
		void testModify() throws NotFoundException, InvalidDataException {
			Actor actor = actorService.getOne(200).get();
			actor.setLastName("PRUEBAAAAAAAA");
			actorService.modify(actor);
			
			Actor actor2 = actorService.getOne(200).get();
			assertEquals("PRUEBAAAAAAAA", actor2.getLastName());
		}

		@Test
		void testDeleteById() throws DuplicateKeyException, InvalidDataException {
			Actor actor = new Actor(202, "NOMBREPRUEBA", "APELLIDOPRUEBA");
			actorService.add(actor);
			actorService.deleteById(202);
			assertTrue(daoActorRepository.findByActorIdEquals(202).size() == 0);
		}
		
		@Test
		void testCreateActor() {
			actorService.deleteById(300);
			List<Film> filmactors = new ArrayList<Film>();
			Film filmOne = new Film(1);
			filmOne.setTitle("Pelicula 1");
			filmactors.add(filmOne);
			Film filmTwo = new Film(2);
			filmOne.setTitle("Pelicula 2");
			filmactors.add(filmTwo);
			actorService.createActor(300, "PEPITO", "GRILLITO", filmactors);
			
			Actor actor = actorService.getOne(300).get();
			assertEquals(300, actor.getActorId());
			assertEquals("PEPITO", actor.getFirstName());
			assertEquals("GRILLITO", actor.getLastName());
			assertEquals(filmactors, actor.getFilms());
		}
	}
	
	class Ko{
		
		@Test
		void testCreateErroneo() {
			
		}
	}
}
