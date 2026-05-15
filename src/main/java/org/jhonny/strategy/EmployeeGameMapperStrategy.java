package org.jhonny.strategy;

import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.dto.EmployeeGameRequest;
import org.jhonny.models.Employee;
import org.jhonny.models.EmployeeGame;

@ApplicationScoped
public class EmployeeGameMapperStrategy implements EmployeeMapperStrategy<EmployeeGameRequest> {
    @Override
    public Class<EmployeeGameRequest> supports() {
        return EmployeeGameRequest.class;
    }

    @Override
    public Employee toEntity(EmployeeGameRequest request) {
        EmployeeGame employee = new EmployeeGame();
        employee.setCi(request.getCi());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setType(request.getType());
        employee.setRol(request.getRol());
        return employee;
    }
}
