package com.example.domains.entities.dtos;

import lombok.Value;

@Value
public class ActorDTO {
	
	private int actorId;
	private String firstName;
	private String lastName;
	private int edad;
}
