package com.example.domains.entities.dtos;

import com.example.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class FilmDto {
	
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("title")
	private String title;
	
	public static FilmDto from(Film source) {
		return new FilmDto(source.getFilmId(), source.getTitle());
	}
	
	public static Film from(FilmDto target) {
		return new Film(target.getFilmId(), target.getTitle());
	}

}
