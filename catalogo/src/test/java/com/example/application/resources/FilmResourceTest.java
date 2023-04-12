package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.application.resources.ActorResourceTest.ActorShortMock;
import com.example.core.SpaceCamelCase;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.FilmDto;
import com.example.domains.entities.dtos.LanguageDTO;
import com.example.exceptions.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;


@WebMvcTest(FilmResource.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayNameGeneration(SpaceCamelCase.class)
class FilmResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private FilmService srvFilmService;
	
	@MockBean
	private ActorService srvActorService;
	
	@MockBean
	private CategoryService srvCategoryService;

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
			List<FilmDto> listFilms = new ArrayList<>(
					Arrays.asList(
							new FilmDto(1, "Pelicula 1"),
							new FilmDto(2, "Pelicula 2"),
							new FilmDto(3, "Pelicula 3")));
			when(srvFilmService.getByProjection(FilmDto.class)).thenReturn(listFilms);
			mockMvc.perform(get("/api/peliculas/v1"))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3));
		}

		@Test
		void testGetAllPageable() throws Exception {
			List<FilmDto> listFilms = new ArrayList<>(
					Arrays.asList(
							new FilmDto(1, "Pelicula 1"),
							new FilmDto(2, "Pelicula 2"),
							new FilmDto(3, "Pelicula 3")));
			when(srvFilmService.getByProjection(PageRequest.of(0, 20), FilmDto.class))
				.thenReturn(new PageImpl<>(listFilms));
			mockMvc.perform(get("/api/peliculas/v1").queryParam("page", "0"))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.content.size()").value(3),
						jsonPath("$.size").value(3));
		}

		@Test
		void testGetOne() throws Exception {
			int id = 1;
			var ele = new Film(1, "Pelicula 1");
			
			when(srvFilmService.getOne(id)).thenReturn(Optional.of(ele));
			mockMvc.perform(get("/api/peliculas/v1/{id}", id))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").value(id))
		        .andExpect(jsonPath("$.title").value(ele.getTitle()))
		        .andDo(print());
		}

		@Test
		void testGetActors() throws Exception {
			int id = 1;
			var ele = new Film(1, "Pelicula 1");
			
			List<Actor> listActors = new ArrayList<>(
					Arrays.asList(
							new Actor(1, "Actor 1", "Apellido 1"),
							new Actor(2, "Actor 2", "Apellido 1"),
							new Actor(3, "Actor 3", "Apellido 1"),
							new Actor(4, "Actor 4", "Apellido 1")));
			ele.setActors(listActors);
			
			when(srvFilmService.getOne(id)).thenReturn(Optional.of(ele));
			
			mockMvc.perform(get("/api/peliculas/v1/{id}/actores", id))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(4))
		        .andDo(print());
		}
		
		@Test
		void testGetCategories() throws Exception {
			int id = 1;
			var ele = new Film(1, "Pelicula 1");
			
			List<Category> listCategories = new ArrayList<>(
					Arrays.asList(
							new Category(1, "Category 1"),
							new Category(2, "Category 2"),
							new Category(3, "Category 3")));
			ele.setCategories(listCategories);
			
			when(srvFilmService.getOne(id)).thenReturn(Optional.of(ele));
			
			mockMvc.perform(get("/api/peliculas/v1/{id}/categorias", id))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3))
		        .andDo(print());
		}
		
		@Disabled
		@Test
		void testCreate() throws JsonProcessingException, Exception {
			int id = 1;
			var ele = new Language(1, "Lengua 1");
			
			when(srvFilmService.add(ele)).thenReturn(ele);
			mockMvc.perform(post("/api/idiomas/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(LanguageDTO.from(ele))))
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/idiomas/v1/1"))
		        .andDo(print());
		}

		@Disabled
		@Test
		void testUpdate() throws JsonProcessingException, Exception {
			int id = 1;
			var ele = new Language(1, "Lengua 1");
			when(srvLanguageService.modify(ele)).thenReturn(ele);
			mockMvc.perform(put("/api/idiomas/v1/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(LanguageDTO.from(ele))))
					.andExpect(status().isNoContent())
			        .andDo(print());
		}

		@Disabled
		@Test
		void testDelete() throws Exception {
			int id = 1;
			var ele = new Language(1, "Lengua 1");
			srvLanguageService.add(ele);
			mockMvc.perform(delete("/api/idiomas/v1/{id}", 1))
					.andExpect(status().isNoContent())
			        .andDo(print());
		}
		
	}
	
	@Nested
	class Negative{
		
		@Disabled
		@Test
		void testGetOne404() throws Exception {
			int id = 1;
			var ele = new Language(1, "Lengua 1");
			when(srvLanguageService.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(get("/api/idiomas/v1/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
		@Disabled
		@Test
		void testUpdateFailIdentificadores() throws JsonProcessingException, Exception {
			int id = 2;
			var ele = new Language(1, "Lengua 1");
			mockMvc.perform(put("/api/idiomas/v1/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(LanguageDTO.from(ele))))
					.andExpect(status().isBadRequest())
			        .andDo(print());
		}
		
		@Disabled
		@Test
		void testUpdateFailContent() throws JsonProcessingException, Exception {
			int id = 1;
			var ele = new Language(1, "Lengua 1");
			when(srvLanguageService.modify(ele)).thenThrow(InvalidDataException.class);
			mockMvc.perform(put("/api/idiomas/v1/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(LanguageDTO.from(ele))))
					.andExpect(status().isBadRequest())
			        .andDo(print());
		}
		
		@Disabled
		@Test
		void testDeleteErroneo() throws JsonProcessingException, Exception {
			mockMvc.perform(delete("/api/idiomas/v1/{id}", "1k"))
					.andExpect(status().isBadRequest())
			        .andDo(print());
		}
	}
}
