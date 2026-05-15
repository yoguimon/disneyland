package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jhonny.exception.BuyerNotFoundException;
import org.jhonny.models.Client;
import org.jhonny.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ApplicationScoped
public class ClientService {

    private final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    @Inject
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClient(Long id){
        Client client = clientRepository.findById(id);

        if(Objects.isNull(client)) {
            LOGGER.error("Client not found");
            throw new BuyerNotFoundException("Client not found");

        }
        return client;
    }
}
