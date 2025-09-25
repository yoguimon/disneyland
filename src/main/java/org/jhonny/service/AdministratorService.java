package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.jhonny.dto.PersonDto;
import org.jhonny.dto.Response;
import org.jhonny.models.Buyer;
import org.jhonny.models.Employee;
import org.jhonny.models.User;
import org.jhonny.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Transactional
public class AdministratorService {

    private final Logger LOGGER = LoggerFactory.getLogger(AdministratorService.class);
    private final AdminRepository adminRepository;

    @Inject
    public AdministratorService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
//strategy,factory
    public Response addPerson(PersonDto person) {

        try{
            switch (person.getType()) {
                case EMPLOYEE:
                    Employee employee = Employee.builder()
                            .ci(person.getCi())
                            .firstName(person.getFirstName())
                            .lastName(person.getLastName())
                            .email(person.getEmail())
                            .type(person.getType())
                            .build();
                    adminRepository.save(employee);

                    LOGGER.info("Added employee {}",  employee);

                    User user = User.builder()
                            .employee(employee)
                            .username(person.getEmail())
                            .password(person.getLastName())
                            .build();
                    adminRepository.save(user);

                    LOGGER.info("Added user {}",  user);

                    return new  Response("success");

                case BUYER:
                    Buyer buyer = Buyer.builder()
                            .ci(person.getCi())
                            .firstName(person.getFirstName())
                            .lastName(person.getLastName())
                            .email(person.getEmail())
                            .type(person.getType())
                            .build();
                    adminRepository.save(buyer);

                    LOGGER.info("Added buyer {}",  buyer);
                    return new Response("success");

                default:
                    LOGGER.error("Invalid type of person");
                    return new Response("Invalid kind of person");
            }
        }catch(Exception e){

            LOGGER.error("something happened {}", e.getMessage());
            return new Response(e.getMessage());
        }
    }
}
