package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.PersonRequest;
import org.jhonny.dto.PersonResponse;
import org.jhonny.models.Buyers;
import org.jhonny.models.Employees;
import org.jhonny.models.Users;
import org.jhonny.repository.BuyerRepository;
import org.jhonny.repository.EmployeeRepository;
import org.jhonny.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AdministratorService {

    private final Logger LOGGER = LoggerFactory.getLogger(AdministratorService.class);

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final BuyerRepository buyerRepository;

    @Inject
    public AdministratorService(UserRepository userRepository, EmployeeRepository employeeRepository,
                                BuyerRepository buyerRepository) {

        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.buyerRepository = buyerRepository;

    }
    ///strategy,factory
    @Transactional
    public PersonResponse addPerson(PersonRequest person) throws Exception {

        switch (person.typePerson()) {
            case EMPLOYEE:
                Employees employee = Employees.builder()
                        .ci(person.ci())
                        .firstName(person.firstName())
                        .lastName(person.lastName())
                        .email(person.email())
                        .type(person.typePerson())
                        .build();
                employeeRepository.persist(employee);

                LOGGER.info("Added employee {}",  employee);

                Users user = Users.builder()
                        .employee(employee)
                        .username(person.email())
                        .password(person.lastName())
                        .build();
                userRepository.persist(user);

                LOGGER.info("Added user {}",  user);

                return new PersonResponse(
                        "New employee added successfully",
                        employee
                );

            case BUYER:
                Buyers buyer = Buyers.builder()
                        .ci(person.ci())
                        .firstName(person.firstName())
                        .lastName(person.lastName())
                        .email(person.email())
                        .type(person.typePerson())
                        .build();
                buyerRepository.persist(buyer);

                LOGGER.info("Added buyer {}",  buyer);

                return new PersonResponse(
                        "New buyer added successfully",
                        buyer
                );

            default:
                LOGGER.error("Invalid type of person");
                throw new Exception("Invalid type of person");
        }
    }
}
