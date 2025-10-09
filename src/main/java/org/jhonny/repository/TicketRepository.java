package org.jhonny.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.models.Tickets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TicketRepository extends EntityRepository<Tickets> {

}
