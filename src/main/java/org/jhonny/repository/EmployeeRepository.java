package org.jhonny.repository;

import org.jhonny.dto.EmployeeRequest;
import org.jhonny.dto.EmployeeResponse;
import org.jhonny.utils.EmployeeType;

public interface EmployeeRepository {
    EmployeeResponse addEmployee(EmployeeRequest person);
    EmployeeType getType();
}
