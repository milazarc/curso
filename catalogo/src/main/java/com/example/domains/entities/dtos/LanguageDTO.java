package com.example.domains.entities.dtos;

import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class LanguageDTO {

	@JsonProperty("id")
	private int languageId;
	@JsonProperty("nombre")
	private String name;
	
	public static LanguageDTO from(Language target) {
		return new LanguageDTO(target.getLanguageId(), target.getName());
	}

	public static Language from(LanguageDTO target) {
		return new Language(target.getLanguageId(), target.getName());
	}

}
