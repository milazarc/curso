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

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping(path = { "/api/categorias/v1", "/api/categories" })
public class CategoryResource {
	@Autowired
	CategoryService srvCategoryService;
	
	
	//GET
	
	@GetMapping
	public List<CategoryDTO> getAll(@RequestParam(required = false) String sort) {
		if(sort != null) {
			return (List<CategoryDTO>)srvCategoryService.getByProjection(Sort.by(sort), CategoryDTO.class);
		}
		return srvCategoryService.getByProjection(CategoryDTO.class);
	}
//	
//	@GetMapping(params = "page")
//	public Page<ActorShort> getAll(Pageable pageable) {
//		return srv.getByProjection(pageable, ActorShort.class);
//	}
//
//	@GetMapping(path = "/{id}")
//	public ActorDTO getOne(@PathVariable int id) throws NotFoundException {
//		var item = srv.getOne(id);
//		if(item.isEmpty())
//			throw new NotFoundException();
//		return ActorDTO.from(item.get());
//	}
//	
//	@GetMapping(path = "/{id}/pelis")
//	@Transactional
//	public List<ElementoDTO<Integer, String>> getPelis(@PathVariable int id) throws NotFoundException {
//		var item = srv.getOne(id);
//		if(item.isEmpty())
//			throw new NotFoundException();
//		return item.get().getFilms().stream().map(o -> new ElementoDTO<>(
//				o.getFilmId(), 
//				o.getTitle()))
//				.toList();
//	}
//	
//	//POST
//	
//	@PostMapping
//	public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
//		var newItem = srv.add(ActorDTO.from(item));
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//			.buildAndExpand(newItem.getActorId()).toUri();
//		return ResponseEntity.created(location).build();
//
//	}
//	
//	//PUT
//	
//	@PutMapping("/{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
//		if(id != item.getActorId())
//			throw new BadRequestException("No coinciden los identificadores");
//		srv.modify(ActorDTO.from(item));
//	}
//	
//	//DELETE
//	
//	@DeleteMapping("/{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void delete(@PathVariable int id) {
//		srv.deleteById(id);
//	}
//	
//	
//	






}
