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

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.FilmDto;
import com.example.domains.entities.dtos.LanguageDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping(path = { "/api/idiomas/v1", "/api/languages" })
public class LanguageResource {
	@Autowired
	LanguageService srvLanguageService;
	
	
	//GET
	
	@GetMapping
	public List<LanguageDTO> getAll(@RequestParam(required = false) String sort) {
		if(sort != null) {
			return (List<LanguageDTO>)srvLanguageService.getByProjection(Sort.by(sort), LanguageDTO.class);
		}
		return srvLanguageService.getByProjection(LanguageDTO.class);
	}
	
	@GetMapping(params = "page")
	public Page<LanguageDTO> getAll(Pageable pageable) {
		return srvLanguageService.getByProjection(pageable, LanguageDTO.class);
	}

	@GetMapping(path = "/{id}")
	public LanguageDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srvLanguageService.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return LanguageDTO.from(item.get());
	}
	
	@GetMapping(path = "/{id}/pelis")
	@Transactional
	public List<FilmDto> getPelis(@PathVariable int id) throws NotFoundException {
		var item = srvLanguageService.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilms().stream().map(o -> FilmDto.from(o)).toList();
	}
	
	@GetMapping(path = "/{id}/pelisVO")
	@Transactional
	public List<FilmDto> getPelisVO(@PathVariable int id) throws NotFoundException {
		var item = srvLanguageService.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmsVO().stream().map(o -> FilmDto.from(o)).toList();
	}
	
	//POST
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody LanguageDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srvLanguageService.add(LanguageDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getLanguageId()).toUri();
		return ResponseEntity.created(location).build();

	}
	
	//PUT
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody LanguageDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getLanguageId())
			throw new BadRequestException("No coinciden los identificadores");
		srvLanguageService.modify(LanguageDTO.from(item));
	}
	
	//DELETE
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srvLanguageService.deleteById(id);
	}
	
	
	






}
