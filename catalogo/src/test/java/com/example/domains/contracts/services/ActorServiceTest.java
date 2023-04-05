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
	
	@Autowired
	FilmService filmService;
	
	
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
		void testAdd() throws DuplicateKeyException, InvalidDataException {
			Actor actor = new Actor(0, "NOMBREPRUEBAA", "APELLIDOPRUEBA");
			actorService.add(actor);
			
			Actor actor2 = daoActorRepository.findByFirstName("NOMBREPRUEBAA");
			assertEquals("NOMBREPRUEBAA", actor2.getFirstName());
			actorService.deleteById(actor2.getActorId());
			
		}

		@Test
		void testModify() throws NotFoundException, InvalidDataException {
			Actor actor = actorService.getOne(200).get();
			actor.setLastName("PRUEBAAAAAAAA");
			actorService.modify(actor);
			
			Actor actor2 = actorService.getOne(200).get();
			assertEquals("PRUEBAAAAAAAA", actor2.getLastName());
		}

		@Test
		@Transactional
		void testDeleteById() throws DuplicateKeyException, InvalidDataException {
			Actor actor = new Actor(240, "NOMBREPRUEBA", "APELLIDOPRUEBA");
			actorService.add(actor);
			Actor actor2 = daoActorRepository.findByFirstName("NOMBREPRUEBA");
			actorService.deleteById(actor2.getActorId());
			assertTrue(daoActorRepository.findByActorIdEquals(actor2.getActorId()).size() == 0);
		}
		

	}
	
	@Nested
	class Ko{
		
		@Test
		void testAddErroneo() throws DuplicateKeyException, InvalidDataException {
			Actor actor = new Actor(0, "NOMBREPRUEBABBB", "APELLIDOPRUEBAaaaaaaa");
			
			
			assertThrows(InvalidDataException.class, () -> actorService.add(actor));
			
		}
	}
}
