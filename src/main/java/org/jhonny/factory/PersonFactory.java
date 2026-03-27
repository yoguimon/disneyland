package org.jhonny.factory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import org.jhonny.repository.PersonRepository;
import org.jhonny.utils.PersonType;


import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PersonFactory {

    private final Map<PersonType, PersonRepository> allInstancesMapOfPersonRepository = new HashMap<>();

    @Inject
    public PersonFactory(Instance<PersonRepository> personRepositoryInstances) {
        for (PersonRepository instance : personRepositoryInstances) {
            allInstancesMapOfPersonRepository.put(instance.getType(), instance);
        }
    }

    public PersonRepository getPerson(PersonType type){
        return allInstancesMapOfPersonRepository.get(type);
    }
}
