package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.EmployeeRequest;
import org.jhonny.dto.EmployeeResponse;
import org.jhonny.exception.EmployeeNotFoundException;
import org.jhonny.factory.EmployeeFactory;
import org.jhonny.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ApplicationScoped
public class EmployeeService {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeFactory employeeFactory;

    @Inject
    public EmployeeService(EmployeeFactory employeeFactory) {
        this.employeeFactory = employeeFactory;
    }

    @Transactional
    public EmployeeResponse addEmployee(EmployeeRequest employee) {
        EmployeeRepository employeeRepository = employeeFactory.getEmployee(employee.typePerson());

        if (Objects.isNull(employeeRepository)) {
            LOGGER.error("No repository found for type {}", employee.typePerson());
            throw new EmployeeNotFoundException("Employee not found");
        }

        return employeeRepository.addEmployee(employee);
    }

}
