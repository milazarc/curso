package com.example.application.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CatalogoServiceImplTest {

	@Autowired
	CatalogoService catalogoService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void novedadesTest() {
		catalogoService.novedades(Timestamp.valueOf("2019-01-01 00:00:00"));
	}

}
