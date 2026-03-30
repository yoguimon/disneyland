package org.jhonny.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.dto.EmployeeRequest;
import org.jhonny.dto.EmployeeResponse;
import org.jhonny.exception.BuyerPersistenceException;
import org.jhonny.models.Client;
import org.jhonny.utils.EmployeeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    private final Logger LOGGER = LoggerFactory.getLogger(ClientRepository.class);
}
