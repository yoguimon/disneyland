package org.jhonny.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.dto.PersonRequest;
import org.jhonny.dto.PersonResponse;
import org.jhonny.exception.BuyerPersistenceException;
import org.jhonny.models.Buyer;
import org.jhonny.utils.PersonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class BuyerRepository implements PanacheRepository<Buyer>, PersonRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(BuyerRepository.class);


    @Override
    public PersonResponse addPerson(PersonRequest person) throws Exception {
        try{
            Buyer buyer = Buyer.builder()
                    .ci(person.ci())
                    .firstName(person.firstName())
                    .lastName(person.lastName())
                    .email(person.email())
                    .type(person.typePerson())
                    .build();
            this.persist(buyer);

            LOGGER.info("Added buyer {}",  buyer);

            return new PersonResponse(
                    "New buyer added successfully",
                    buyer
            );
        }catch(Exception e){
            LOGGER.error("Buyer not added successfully");
            throw new BuyerPersistenceException("Buyer not added successfully");
        }
    }

    @Override
    public PersonType getType() {
        return PersonType.BUYER;
    }
}
