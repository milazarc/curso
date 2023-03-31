package com.example.domains.contracts.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

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
		var item = new Actor(0, "Pepito", "GRILLO");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00.00"));
		em.persist(item);
		
		item = new Actor(0, "Carmelo", "COTON");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00.00"));
		em.persist(item);
		
		item = new Actor(0, "Capitan", "TAN");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00.00"));
		em.persist(item);
	}
	
	
	@Test
	void testAll() {
		assertEquals(3, daoActorRepository.findAll().size());
	}
	


}
