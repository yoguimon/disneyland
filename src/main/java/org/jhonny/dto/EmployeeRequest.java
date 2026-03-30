package org.jhonny.dto;

import org.jhonny.utils.EmployeeType;

public record EmployeeRequest(
        String ci, String firstName, String lastName, String email, EmployeeType typePerson, Long gameId) { }
