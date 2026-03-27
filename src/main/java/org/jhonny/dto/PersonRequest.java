package org.jhonny.dto;

import org.jhonny.utils.PersonType;

public record PersonRequest(String ci, String firstName, String lastName, String email, PersonType typePerson) { }
