package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.services.FilmService;

@SpringBootApplication
public class FilmCatalogApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(FilmCatalogApplication.class, args);
	}
	
	@Autowired
	FilmService srvFilmService;
	
	@Override
	public void run(String... args) throws Exception{
		System.out.println("-----------> Aplicacion iniciada");
	}

}
