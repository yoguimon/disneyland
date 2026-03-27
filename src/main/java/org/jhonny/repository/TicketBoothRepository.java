package org.jhonny.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.models.TicketBooth;

@ApplicationScoped
public class TicketBoothRepository implements PanacheRepository<TicketBooth> {

}
