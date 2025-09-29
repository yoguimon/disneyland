package org.jhonny.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jhonny.dto.PersonRequest;
import org.jhonny.dto.PersonResponse;
import org.jhonny.exception.EmployeePersistenceException;
import org.jhonny.models.Employees;
import org.jhonny.models.Users;
import org.jhonny.utils.TypePerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EmployeeRepository extends EntityRepository<Employees> implements PersonRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeRepository.class);

    private final UserRepository userRepository;

    @Inject
    public EmployeeRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PersonResponse addPerson(PersonRequest person) throws Exception {
        try{
            Employees employee = Employees.builder()
                    .ci(person.ci())
                    .firstName(person.firstName())
                    .lastName(person.lastName())
                    .email(person.email())
                    .type(person.typePerson())
                    .build();
            this.persist(employee);

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

        }catch(Exception e){

            LOGGER.error("Employee not added successfully");
            throw new EmployeePersistenceException("Employee not added successfully");
        }
    }

    @Override
    public TypePerson getType() {
        return TypePerson.EMPLOYEE;
    }
}
