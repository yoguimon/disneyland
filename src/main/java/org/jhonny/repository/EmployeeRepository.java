package org.jhonny.repository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.jhonny.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class EmployeeRepository extends EntityRepository {
    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeRepository.class);

    @Inject
    public EmployeeRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public Employee find(Employee employee) {
        return entityManager.find(employee.getClass(), employee);
    }
}
