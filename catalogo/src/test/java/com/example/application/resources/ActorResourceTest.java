package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.core.SpaceCamelCase;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.exceptions.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorResource.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayNameGeneration(SpaceCamelCase.class)
class ActorResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ActorService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Value
	static class ActorShortMock implements ActorShort {
		int actorId;
		String nombre;
	}
	
	@Nested
	class Affirmative{
		
		@Test
		void testGetAllString() throws Exception {
			List<ActorShort> lista = new ArrayList<>(
			        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
			        		new ActorShortMock(2, "Carmelo Coton"),
			        		new ActorShortMock(3, "Capitan Tan")));
			when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
			mockMvc.perform(get("/api/actores/v1").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3)
						);
		}

		@Test
		void testGetAllPageable() throws Exception {
			List<ActorShort> lista = new ArrayList<>(
			        Arrays.asList(
			        		new ActorShortMock(1, "Pepito Grillo"),
			        		new ActorShortMock(2, "Carmelo Coton"),
			        		new ActorShortMock(3, "Capitan Tan")));

			when(srv.getByProjection(PageRequest.of(0, 20), ActorShort.class))
				.thenReturn(new PageImpl<>(lista));
			mockMvc.perform(get("/api/actores/v1").queryParam("page", "0"))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.content.size()").value(3),
					jsonPath("$.size").value(3));
		}
		
		@Test
		void testCreate() throws Exception {
			int id = 1;
			var ele = new Actor(id, "Pepito", "Grillo");
			when(srv.add(ele)).thenReturn(ele);
			mockMvc.perform(post("/api/actores/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
				)
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/actores/v1/1"))
		        .andDo(print())
		        ;
		}	
		
		@Test
		void testGetOne() throws Exception {
			int id = 1;
			var ele = new Actor(id, "Pepito", "Grillo");
			when(srv.getOne(id)).thenReturn(Optional.of(ele));
			mockMvc.perform(get("/api/actores/v1/{id}", id))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").value(id))
		        .andExpect(jsonPath("$.nombre").value(ele.getFirstName()))
		        .andExpect(jsonPath("$.apellidos").value(ele.getLastName()))
		        .andDo(print());
		}
		

		

		@Test
		void testUpdate() throws JsonProcessingException, Exception {
			int id = 1;
			var ele = new Actor(id, "Pepito", "Grillo");
			when(srv.modify(ele)).thenReturn(ele);
			mockMvc.perform(put("/api/actores/v1/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(ActorDTO.from(ele))))
					.andExpect(status().isNoContent())
			        .andDo(print())
			        ;
		}
		
		@Test
		void testGetPelis() throws Exception {
			int id = 1;
			Actor actor = new Actor(id, "Pepito", "Grillo");
			List<Film> peliculas = new ArrayList<Film>();
			peliculas.add(new Film(1, "Pelicula 1"));
			peliculas.add(new Film(2, "Pelicula 2"));
			peliculas.add(new Film(3, "Pelicula 3"));
			actor.setFilms(peliculas);
			when(srv.getOne(id)).thenReturn(Optional.of(actor));
			
			
			mockMvc.perform(get("/api/actores/v1/{id}/pelis", id))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.size()").value(3))
			        .andDo(print());
		}
		
	}
	
	@Nested
	class Negative{
		
		@Test
		void testGetOne404() throws Exception {
			int id = 1;
			var ele = new Actor(id, "Pepito", "Grillo");
			when(srv.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(get("/api/actores/v1/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
		@Test
		void testUpdate_FailIdentificadores() throws JsonProcessingException, Exception {
			int id = 2;
			var ele = new Actor(1, "Pepito", "Grillo");
			mockMvc.perform(put("/api/actores/v1/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(ActorDTO.from(ele))))
					.andExpect(status().isBadRequest())
			        .andDo(print());
		}
		
		@Test
		void testUpdate_FailContent() throws JsonProcessingException, Exception {
			int id = 1;
			var ele = new Actor(id, "", "Grillo");
			when(srv.modify(ele)).thenThrow(InvalidDataException.class);
			mockMvc.perform(put("/api/actores/v1/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(ActorDTO.from(ele))))
					.andExpect(status().isBadRequest())
			        .andDo(print());
		}
		
		@Test
		void testDeleteErroneo() throws JsonProcessingException, Exception {
			mockMvc.perform(delete("/api/actores/v1/{id}", "1k"))
					.andExpect(status().isBadRequest())
			        .andDo(print());
		}
		
	}
	
	

	
	


}