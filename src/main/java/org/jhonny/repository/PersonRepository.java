package org.jhonny.repository;

import org.jhonny.dto.PersonRequest;
import org.jhonny.dto.PersonResponse;
import org.jhonny.utils.TypePerson;

public interface PersonRepository {
    PersonResponse addPerson(PersonRequest person) throws Exception;
    TypePerson getType();
}
