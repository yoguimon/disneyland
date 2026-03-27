package org.jhonny.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jhonny.dto.PersonRequest;
import org.jhonny.dto.PersonResponse;
import org.jhonny.exception.EmployeePersistenceException;
import org.jhonny.models.Employee;
import org.jhonny.models.User;
import org.jhonny.utils.PersonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee>, PersonRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeRepository.class);

    private final UserRepository userRepository;

    @Inject
    public EmployeeRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PersonResponse addPerson(PersonRequest person) throws Exception {
        try{
            Employee employee = Employee.builder()
                    .ci(person.ci())
                    .firstName(person.firstName())
                    .lastName(person.lastName())
                    .email(person.email())
                    .type(person.typePerson())
                    .build();
            this.persist(employee);

            LOGGER.info("Added employee {}",  employee);

            User user = User.builder()
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

    /// 6
    public List<Employee> getListOfEmployeesWithHisResponsableGame(){
        return findAll().stream().toList();
    }

    @Override
    public PersonType getType() {
        return PersonType.EMPLOYEE;
    }
}
