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
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.LanguageDTO;
import com.example.exceptions.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LanguageResource.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayNameGeneration(SpaceCamelCase.class)
class LanguageResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private LanguageService srvLanguageService;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Nested
	class Affirmative{
		@Test
		void testGetAllString() throws Exception {
			List<LanguageDTO> listaLanguageDTOs = new ArrayList<>(
					Arrays.asList(
							new LanguageDTO(1, "Lengua 1"),
							new LanguageDTO(2, "Lengua 2"),
							new LanguageDTO(3, "Lengua 3")));
			when(srvLanguageService.getByProjection(LanguageDTO.class)).thenReturn(listaLanguageDTOs);
			mockMvc.perform(get("/api/idiomas/v1"))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3));
		}

		@Test
		void testGetAllPageable() throws Exception {
			List<LanguageDTO> listaLanguageDTOs = new ArrayList<>(
					Arrays.asList(
							new LanguageDTO(1, "Lengua 1"),
							new LanguageDTO(2, "Lengua 2"),
							new LanguageDTO(3, "Lengua 3")));
			when(srvLanguageService.getByProjection(PageRequest.of(0, 20), LanguageDTO.class))
				.thenReturn(new PageImpl<>(listaLanguageDTOs));
			mockMvc.perform(get("/api/idiomas/v1").queryParam("page", "0"))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.content.size()").value(3),
					jsonPath("$.size").value(3));
		}

		@Test
		void testGetOne() throws Exception {
			int id = 1;
			var ele = new Language(1, "Lengua 1");
			
			when(srvLanguageService.getOne(id)).thenReturn(Optional.of(ele));
			mockMvc.perform(get("/api/idiomas/v1/{id}", id))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").value(id))
		        .andExpect(jsonPath("$.nombre").value(ele.getName()))
		        .andDo(print());
		}

		@Test
		void testGetPelis() throws Exception {
			int id = 1;
			var ele = new Language(1, "Lengua 1");
			
			List<Film> listFilms = new ArrayList<>(
					Arrays.asList(
							new Film(1, "Pelicula 1"),
							new Film(2, "Pelicula 2"),
							new Film(3, "Pelicula 3"),
							new Film(4, "Pelicula 4")));
			ele.setFilms(listFilms);
			
			when(srvLanguageService.getOne(id)).thenReturn(Optional.of(ele));
			
			mockMvc.perform(get("/api/idiomas/v1/{id}/pelis", id))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(4))
		        .andDo(print());
		}

		@Test
		void testGetPelisVo() throws Exception {
			int id = 1;
			var ele = new Language(1, "Lengua 1");
			
			List<Film> listFilms = new ArrayList<>(
					Arrays.asList(
							new Film(1, "PeliculaVo 1"),
							new Film(2, "PeliculaVo 2"),
							new Film(3, "PeliculaVo 3"),
							new Film(4, "PeliculaVo 4"),
							new Film(5, "PeliculaVo 5")));
			ele.setFilmsVO(listFilms);
			
			when(srvLanguageService.getOne(id)).thenReturn(Optional.of(ele));
			
			mockMvc.perform(get("/api/idiomas/v1/{id}/pelisVO", id))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(5))
		        .andDo(print());
		}
		
		
		@Test
		void testCreate() throws JsonProcessingException, Exception {
			int id = 1;
			var ele = new Language(1, "Lengua 1");
			
			when(srvLanguageService.add(ele)).thenReturn(ele);
			mockMvc.perform(post("/api/idiomas/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(LanguageDTO.from(ele))))
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/idiomas/v1/1"))
		        .andDo(print());
		}

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
		
		@Test
		void testGetPelisFail() throws Exception {
			int id = 1;
			
			mockMvc.perform(get("/api/idiomas/v1/{id}/pelis", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
		@Test
		void testGetPelisVoFail() throws Exception {
			int id = 1;
			
			mockMvc.perform(get("/api/idiomas/v1/{id}/pelisVO", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
		
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
		
		@Test
		void testDeleteErroneo() throws JsonProcessingException, Exception {
			mockMvc.perform(delete("/api/idiomas/v1/{id}", "1k"))
					.andExpect(status().isBadRequest())
			        .andDo(print());
		}
	}
	
	
	
}
