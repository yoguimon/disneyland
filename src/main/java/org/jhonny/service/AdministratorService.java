package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.PersonRequest;
import org.jhonny.dto.PersonResponse;
import org.jhonny.exception.PersonNotFoundException;
import org.jhonny.factory.PersonFactory;
import org.jhonny.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ApplicationScoped
public class AdministratorService {

    private final Logger LOGGER = LoggerFactory.getLogger(AdministratorService.class);

    private final PersonFactory personFactory;

    @Inject
    public AdministratorService(PersonFactory personFactory) {
        this.personFactory = personFactory;
    }

    @Transactional
    public PersonResponse addPerson(PersonRequest person) throws Exception {
        PersonRepository personRepository = personFactory.getPerson(person.typePerson());

        if(Objects.isNull(personRepository)) {
            LOGGER.error("No repository found for type {}", person.typePerson());
            throw new PersonNotFoundException("Person not found");
        }

        return personRepository.addPerson(person);
    }
}
