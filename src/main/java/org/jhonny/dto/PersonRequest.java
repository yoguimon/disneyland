package org.jhonny.dto;

import org.jhonny.utils.TypePerson;

public record PersonRequest(String ci, String firstName, String lastName, String email, TypePerson typePerson) { }
