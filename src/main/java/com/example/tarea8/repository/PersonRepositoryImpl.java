package com.example.tarea8.repository;


import org.springframework.stereotype.Service;

import com.example.tarea8.dto.PersonDTO;

@Service
public class PersonRepositoryImpl implements PersonRepository {

	@Override
	public void persist(final PersonDTO personDTO) {
		// implementation of persisting to database
	}
}
