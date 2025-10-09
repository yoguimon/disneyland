package org.jhonny.dto;

import org.jhonny.models.Persons;

public record PersonResponse(String message, Persons person) { }
