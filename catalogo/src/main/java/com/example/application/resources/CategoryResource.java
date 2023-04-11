package com.example.application.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.domains.contracts.services.CategoryService;


@RestController
@RequestMapping(path = { "/api/categorias/v1", "/api/categories" })
public class CategoryResource {
	@Autowired
	
	

}
