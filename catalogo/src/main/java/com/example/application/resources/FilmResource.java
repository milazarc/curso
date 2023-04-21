package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.FilmDetailsDTO;
import com.example.domains.entities.dtos.FilmDto;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping(path = { "/api/peliculas/v1", "/api/films" })
public class FilmResource {
	@Autowired
	FilmService srvFilmService;
	
	//GET
	
	@GetMapping
	public List<FilmDto> getAll(@RequestParam(required = false) String sort) {
		if(sort != null) {
			return (List<FilmDto>)srvFilmService.getByProjection(Sort.by(sort), FilmDto.class);
		}
		return srvFilmService.getByProjection(FilmDto.class);
	}
	
	@GetMapping(params = "page")
	public Page<FilmDto> getAll(Pageable pageable) {
		return srvFilmService.getByProjection(pageable, FilmDto.class);
	}

	@GetMapping(path = "/{id}")
	public FilmDto getOne(@PathVariable int id) throws NotFoundException {
		var item = srvFilmService.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return FilmDto.from(item.get());
	}
	
	@GetMapping(path = "/{id}/complete")
	public FilmEditDTO getOneComplete(@PathVariable int id) throws NotFoundException {
		var item = srvFilmService.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return FilmEditDTO.from(item.get());
	}
	
	@GetMapping(path = "/{id}/actores")
	@Transactional
	public List<ActorDTO> getActores(@PathVariable int id) throws NotFoundException {
		var item = srvFilmService.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getActors().stream().map(o -> ActorDTO.from(o)).toList();
	}
	
	@GetMapping(path = "/{id}/categorias")
	@Transactional
	public List<CategoryDTO> getCategorias(@PathVariable int id) throws NotFoundException {
		var item = srvFilmService.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getCategories().stream().map(o -> CategoryDTO.from(o)).toList();
	}	
	
	//POST
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody FilmEditDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srvFilmService.add(FilmEditDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	//PUT
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody FilmEditDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getFilmId())
			throw new BadRequestException("No coinciden los identificadores");
		srvFilmService.modify(FilmEditDTO.from(item));
	}
	
	//DELETE
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srvFilmService.deleteById(id);
	}
	
	@DeleteMapping("/{id}/categoria/{categoryId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCategoria(@PathVariable int id, @PathVariable int categoryId) throws NotFoundException, InvalidDataException {
		var item = srvFilmService.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		item.get().removeCategoryId(categoryId);
		srvFilmService.modify(item.get());
	}
	
	@DeleteMapping("/{id}/actor/{actorId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteActor(@PathVariable int id, @PathVariable int actorId) throws NotFoundException, InvalidDataException {
		var item = srvFilmService.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		item.get().removeActorId(actorId);
		srvFilmService.modify(item.get());
	}
	
	
	
	
	






}
