package org.jhonny.strategy;

import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.dto.EmployeeAdminRequest;
import org.jhonny.models.Employee;
import org.jhonny.models.EmployeeAdmin;

@ApplicationScoped
public class EmployeeAdminMapperStrategy implements EmployeeMapperStrategy<EmployeeAdminRequest> {
    @Override
    public Class<EmployeeAdminRequest> supports() {
        return EmployeeAdminRequest.class;
    }

    @Override
    public Employee toEntity(EmployeeAdminRequest request) {
        EmployeeAdmin employee = new EmployeeAdmin();
        employee.setCi(request.getCi());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setType(request.getType());
        employee.setLevel(request.getLevel());
        return employee;
    }
}
