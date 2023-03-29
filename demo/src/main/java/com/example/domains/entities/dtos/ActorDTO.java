package com.example.domains.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data @AllArgsConstructor
public class ActorDTO {
	
	private int actorId;
	private String firstName;
	private String lastName;
	private int edad;
}
