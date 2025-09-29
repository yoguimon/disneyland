package org.jhonny.factory;

import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import net.bytebuddy.build.AccessControllerPlugin;
import org.jhonny.repository.PersonRepository;
import org.jhonny.utils.TypePerson;


import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PersonFactory {

    private final Map<TypePerson, PersonRepository> persons = new HashMap<>();

    @Inject
    public PersonFactory(Instance<PersonRepository> repos) {
        for (PersonRepository repo : repos) {
            persons.put(repo.getType(), repo);
        }
    }

    public PersonRepository getPerson(TypePerson type){
        return persons.get(type);
    }
}
