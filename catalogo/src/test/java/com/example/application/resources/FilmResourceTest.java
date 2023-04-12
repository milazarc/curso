package com.example.application.resources;

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

import java.math.BigDecimal;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.core.SpaceCamelCase;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.FilmDto;
import com.example.domains.entities.dtos.FilmEditDTO;
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
	
	List<Category> listCategories;
	List<FilmDto> listFilms;
	List<Actor> listActors;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
		listFilms = new ArrayList<>(
				Arrays.asList(
						new FilmDto(1, "Pelicula 1"),
						new FilmDto(2, "Pelicula 2"),
						new FilmDto(3, "Pelicula 3")));
		listActors = new ArrayList<>(
				Arrays.asList(
						new Actor(1, "Actor 1", "Apellido 1"),
						new Actor(2, "Actor 2", "Apellido 1"),
						new Actor(3, "Actor 3", "Apellido 1"),
						new Actor(4, "Actor 4", "Apellido 1")));
		listCategories = new ArrayList<>(
				Arrays.asList(
						new Category(1, "Category 1"),
						new Category(2, "Category 2"),
						new Category(3, "Category 3")));
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
			when(srvFilmService.getByProjection(FilmDto.class)).thenReturn(listFilms);
			mockMvc.perform(get("/api/peliculas/v1"))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3));
		}

		@Test
		void testGetAllPageable() throws Exception {
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
			var ele = new Film(
					id,
					"Pelicula 1",
					"Descripcion",
					(short) 2000,
					new Language(1, "Lengua 1"),
					new Language(2, "Lengua 2"),
					(byte) 4,
					new BigDecimal(3.1),
					80,
					new BigDecimal(20.5),
					Rating.GENERAL_AUDIENCES);
			ele.setActors(listActors);
			ele.setCategories(listCategories);
			
			when(srvFilmService.getOne(id)).thenReturn(Optional.of(ele));
			mockMvc.perform(get("/api/peliculas/v1/{id}", id))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").value(id))
		        .andExpect(jsonPath("$.title").value(ele.getTitle()))
		        .andDo(print());
		}
		
		@Test
		void testGetOneComplete() throws Exception {
			int id = 1;
			var ele = new Film(
					id,
					"Pelicula 1",
					"Descripcion",
					(short) 2000,
					new Language(1, "Lengua 1"),
					new Language(2, "Lengua 2"),
					(byte) 4,
					new BigDecimal(3.1),
					80,
					new BigDecimal(20.5),
					Rating.GENERAL_AUDIENCES);
			ele.setActors(listActors);
			ele.setCategories(listCategories);
			
			when(srvFilmService.getOne(id)).thenReturn(Optional.of(ele));
			mockMvc.perform(get("/api/peliculas/v1/complete/{id}", id))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.filmId").value(id))
		        .andExpect(jsonPath("$.title").value(ele.getTitle()))
		        .andExpect(jsonPath("$.description").value(ele.getDescription()))
		        .andDo(print());
		}

		@Test
		void testGetActors() throws Exception {
			int id = 1;
			var ele = new Film(1, "Pelicula 1");
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
			ele.setCategories(listCategories);
			
			when(srvFilmService.getOne(id)).thenReturn(Optional.of(ele));
			
			mockMvc.perform(get("/api/peliculas/v1/{id}/categorias", id))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3))
		        .andDo(print());
		}
		
		@Test
		void testCreate() throws JsonProcessingException, Exception {
			int id = 1;
			var ele = new Film(
					id,
					"Pelicula 1",
					"Descripcion",
					(short) 2000,
					new Language(1, "Lengua 1"),
					new Language(2, "Lengua 2"),
					(byte) 4,
					new BigDecimal(3.1),
					80,
					new BigDecimal(20.5),
					Rating.GENERAL_AUDIENCES);
			ele.setActors(listActors);
			ele.setCategories(listCategories);
			
			when(srvFilmService.add(ele)).thenReturn(ele);
			mockMvc.perform(post("/api/peliculas/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(FilmEditDTO.from(ele))))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "http://localhost/api/peliculas/v1/1"))
		        .andDo(print());
		}

		@Test
		void testUpdate() throws JsonProcessingException, Exception {
			int id = 1;
			var ele = new Film(
					id,
					"Pelicula 1",
					"Descripcion",
					(short) 2000,
					new Language(1, "Lengua 1"),
					new Language(2, "Lengua 2"),
					(byte) 4,
					new BigDecimal(3.1),
					80,
					new BigDecimal(20.5),
					Rating.GENERAL_AUDIENCES);
			ele.setActors(listActors);
			ele.setCategories(listCategories);
			
			when(srvFilmService.modify(ele)).thenReturn(ele);
			mockMvc.perform(put("/api/peliculas/v1/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(FilmEditDTO.from(ele))))
					.andExpect(status().isNoContent())
			        .andDo(print());
		}

		@Test
		void testDelete() throws Exception {
			int id = 1;
			var ele = new Film(
					id,
					"Pelicula 1",
					"Descripcion",
					(short) 2000,
					new Language(1, "Lengua 1"),
					new Language(2, "Lengua 2"),
					(byte) 4,
					new BigDecimal(3.1),
					80,
					new BigDecimal(20.5),
					Rating.GENERAL_AUDIENCES);
			ele.setActors(listActors);
			ele.setCategories(listCategories);
			srvFilmService.add(ele);
			mockMvc.perform(delete("/api/peliculas/v1/{id}", 1))
					.andExpect(status().isNoContent())
			        .andDo(print());
		}
		
		@Test
		void testDeleteCategoria() throws Exception {
			int id = 1;
			var ele = new Film(
					id,
					"Pelicula 1",
					"Descripcion",
					(short) 2000,
					new Language(1, "Lengua 1"),
					new Language(2, "Lengua 2"),
					(byte) 4,
					new BigDecimal(3.1),
					80,
					new BigDecimal(20.5),
					Rating.GENERAL_AUDIENCES);
			ele.setActors(listActors);
			ele.setCategories(listCategories);
			when(srvFilmService.getOne(ele.getFilmId())).thenReturn(Optional.of(ele));
			mockMvc.perform(delete("/api/peliculas/v1/{id}/categoria/1", 1))
					.andExpect(status().isNoContent())
			        .andDo(print());
		}
		
		@Test
		void testDeleteActores() throws Exception {
			int id = 1;
			var ele = new Film(
					id,
					"Pelicula 1",
					"Descripcion",
					(short) 2000,
					new Language(1, "Lengua 1"),
					new Language(2, "Lengua 2"),
					(byte) 4,
					new BigDecimal(3.1),
					80,
					new BigDecimal(20.5),
					Rating.GENERAL_AUDIENCES);
			ele.setActors(listActors);
			ele.setCategories(listCategories);
			when(srvFilmService.getOne(ele.getFilmId())).thenReturn(Optional.of(ele));
			mockMvc.perform(delete("/api/peliculas/v1/{id}/actor/1", 1))
					.andExpect(status().isNoContent())
			        .andDo(print());
		}
		
	}
	
	@Nested
	class Negative{
		
		@Test
		void testGetOne404() throws Exception {
			int id = 1;
			when(srvFilmService.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(get("/api/peliculas/v1/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
		@Test
		void testUpdateFailIdentificadores() throws JsonProcessingException, Exception {
			int id = 2;
			var ele = new Film(
					1,
					"Pelicula 1",
					"Descripcion",
					(short) 2000,
					new Language(1, "Lengua 1"),
					new Language(2, "Lengua 2"),
					(byte) 4,
					new BigDecimal(3.1),
					80,
					new BigDecimal(20.5),
					Rating.GENERAL_AUDIENCES);
			ele.setActors(listActors);
			ele.setCategories(listCategories);
			mockMvc.perform(put("/api/peliculas/v1/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(FilmEditDTO.from(ele))))
					.andExpect(status().isBadRequest())
			        .andDo(print());
		}
		
		@Test
		void testUpdateFailContent() throws JsonProcessingException, Exception {
			int id = 1;
			var ele = new Film(
					id,
					"",
					"Descripcion",
					(short) 2000,
					new Language(1, "Lengua 1"),
					new Language(2, "Lengua 2"),
					(byte) 4,
					new BigDecimal(3.1),
					80,
					new BigDecimal(20.5),
					Rating.GENERAL_AUDIENCES);
			ele.setActors(listActors);
			ele.setCategories(listCategories);
			when(srvFilmService.modify(ele)).thenThrow(InvalidDataException.class);
			mockMvc.perform(put("/api/peliculas/v1/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(FilmEditDTO.from(ele))))
					.andExpect(status().isBadRequest())
			        .andDo(print());
		}
		
		@Test
		void testDeleteErroneo() throws JsonProcessingException, Exception {
			mockMvc.perform(delete("/api/peliculas/v1/{id}", "1k"))
					.andExpect(status().isBadRequest())
			        .andDo(print());
		}
		
		@Test
		void testGetOneFail() throws Exception {
			int id = 1;
			
			when(srvFilmService.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(get("/api/peliculas/v1/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
		@Test
		void testGetOneCompleteFail() throws Exception {
			int id = 1;
			
			when(srvFilmService.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(get("/api/peliculas/v1/complete/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
		@Test
		void testDeleteCategoriaFail() throws Exception {
			int id = 1;

			when(srvFilmService.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(delete("/api/peliculas/v1/{id}/categoria/1", 1))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
		@Test
		void testDeleteActoresFail() throws Exception {
			int id = 1;

			when(srvFilmService.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(delete("/api/peliculas/v1/{id}/actor/1", 1))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
		@Test
		void testGetActorsFail() throws Exception {
			int id = 1;
			when(srvFilmService.getOne(id)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/peliculas/v1/{id}/actores", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
		@Test
		void testGetCategoriesFail() throws Exception {
			int id = 1;
			when(srvFilmService.getOne(id)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/peliculas/v1/{id}/categorias", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
	}
}
