package org.jhonny.dto;

import org.jhonny.models.Person;

public record PersonResponse(String message, Person person) { }
