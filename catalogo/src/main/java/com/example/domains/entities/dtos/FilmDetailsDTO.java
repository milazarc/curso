package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.example.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class FilmDetailsDTO {
	@JsonProperty("filmId")
	private int filmId;
	@JsonProperty("description")
	private String description;
	@JsonProperty("length")
	private int length;
	@JsonProperty("rating")
	private String rating;
	@JsonProperty("releaseYear")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private Short releaseYear;
	@JsonProperty("rentalDuration")
	private byte rentalDuration;
	@JsonProperty("rentalRate")
	private BigDecimal rentalRate;
	@JsonProperty("replacementCost")
	private BigDecimal replacementCost;
	@JsonProperty("title")
	private String title;
	@JsonProperty("language")
	private String language;
	@JsonProperty("languageVO")
	private String languageVO;
	@JsonProperty("actors")
	private List<String> actors;
	@JsonProperty("categories")
	private List<String> categories;
	
	public static FilmDetailsDTO from(Film source) {
		return new FilmDetailsDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating().getValue(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getName(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getName(),
				source.getActors().stream().map(item -> item.getFirstName() + " " + item.getLastName())
					.sorted().toList(),
				source.getCategories().stream().map(item -> item.getName()).sorted().toList()
				);
	}
}
