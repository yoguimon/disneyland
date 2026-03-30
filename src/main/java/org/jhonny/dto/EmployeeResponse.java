package org.jhonny.dto;

import org.jhonny.models.Employee;

public record EmployeeResponse(String message, Employee employee) { }
