package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jhonny.exception.BuyerNotFoundException;
import org.jhonny.models.Buyer;
import org.jhonny.repository.BuyerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ApplicationScoped
public class BuyerService {

    private final Logger LOGGER = LoggerFactory.getLogger(BuyerService.class);

    private final BuyerRepository buyerRepository;

    @Inject
    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public Buyer getBuyer(Long id){
        Buyer buyer = buyerRepository.findById(id);

        if(Objects.isNull(buyer)) {
            LOGGER.error("buyer not found");
            throw new BuyerNotFoundException("buyer not found");

        }
        return buyer;
    }
}
