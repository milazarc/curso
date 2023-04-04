package com.example.domains.entities.dtos;

import com.example.domains.entities.Actor;

import lombok.Value;

@Value
public class ActorShortDTO {
	private int actorId;
	private String firstName;
	private String lastName;
	
	public static ActorShortDTO from(Actor source) {
		return new ActorShortDTO(source.getActorId(), source.getFirstName(), source.getLastName());
	}
}