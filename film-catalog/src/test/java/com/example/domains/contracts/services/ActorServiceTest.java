package com.example.domains.contracts.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
class ActorServiceTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testNovedades() {
		fail("Not yet implemented");
	}

}
