package com.forum.ticketgenerator.model;


import lombok.Data;

import javax.persistence.*;

@Data
public class EvenementDTO {
	private long id;
	private String intitule;

	@Override
	public boolean equals(Object o ) {
		if (o instanceof EvenementDTO) {
			return this.getId() == ((EvenementDTO) o).getId();
		}
		return false;
	}
}