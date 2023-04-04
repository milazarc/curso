package com.example.domains.contracts.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.contracts.repositories.ActorRepository;

@SpringBootTest
class ActorServiceTest {

	@Autowired
	ActorRepository daoActorRepository;
	
	@Autowired
	ActorService actorService;	
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		System.out.println(daoActorRepository.findById(1));
	}

}
