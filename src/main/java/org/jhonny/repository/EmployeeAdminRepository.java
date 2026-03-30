package org.jhonny.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jhonny.dto.EmployeeRequest;
import org.jhonny.dto.EmployeeResponse;
import org.jhonny.models.EmployeeAdmin;
import org.jhonny.utils.EmployeeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EmployeeAdminRepository implements PanacheRepository<EmployeeAdmin>, EmployeeRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeAdminRepository.class);

    private final UserRepository userRepository;

    @Inject
    public EmployeeAdminRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest person) {
        return null;
    }

    @Override
    public EmployeeType getType() {
        return EmployeeType.ADMIN;
    }
}
