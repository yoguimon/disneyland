package org.jhonny.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.models.TicketOffice;

@ApplicationScoped
public class TicketOfficeRepository implements PanacheRepository<TicketOffice> {

}
