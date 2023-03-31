package com.example.domains.contracts.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.support.DaoSupport;

import com.example.domains.entities.Actor;


@DataJpaTest
class ActorRepositoryMemoryTest {
	@Autowired	
	private TestEntityManager em;
	
	@Autowired
	ActorRepository daoActorRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		em.persist(new Actor(0, "Pepito", "GRILLO	"));
		em.persist(new Actor(0, "Carmelo", "COTON"));
		em.persist(new Actor(0, "Capitan", "TAN"));
	}
	
	
	@Test
	void testAll() {
		assertEquals(3, daoActorRepository.findAll().size());
	}
	


}
