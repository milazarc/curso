package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.ioc.EjemplosIoC;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	ActorRepository daoActorRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
//		(new EjemplosIoC()).run();
//		var actor = new Actor(0,"Pepito", "Grillo");
//		daoActorRepository.save(actor);
//		var item = daoActorRepository.findById(201);
//		if(item.isPresent()) {
//			var actor = item.get();
//			actor.setLastName(actor.getLastName().toUpperCase());
//			daoActorRepository.save(actor);
//		}else {
//			System.out.println("Actor no encontrado");
//		}
//		daoActorRepository.findAll().forEach(System.out::println);
		
		daoActorRepository.findTop5ByFirstNameStartingWithOrderByLastNameDesc("P").forEach(System.out::println);
		daoActorRepository.findTop5ByFirstNameStartingWith("P", Sort.by("LastName").descending()).forEach(System.out::println);
		daoActorRepository.findTop5ByFirstNameStartingWith("P", Sort.by("LastName")).forEach(System.out::println);
	}
}
