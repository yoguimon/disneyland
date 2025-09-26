package org.jhonny.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.models.TicketOffices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TicketOfficeRepository extends EntityRepository<TicketOffices> {

}
