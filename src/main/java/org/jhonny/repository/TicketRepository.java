package org.jhonny.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.models.Ticket;

@ApplicationScoped
public class TicketRepository implements PanacheRepository<Ticket> {

}
