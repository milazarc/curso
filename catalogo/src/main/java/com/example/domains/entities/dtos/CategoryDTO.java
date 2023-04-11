package com.example.domains.entities.dtos;

import com.example.domains.entities.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class CategoryDTO {
	@JsonProperty("id")
	private int categoryId;
	@JsonProperty("nombre")
	private String name;
	
	public static CategoryDTO from(Category target) {
		return new CategoryDTO(target.getCategoryId(), target.getName());
	}

	public static Category from(CategoryDTO target) {
		return new Category(target.getCategoryId(), target.getName());
	}
}
