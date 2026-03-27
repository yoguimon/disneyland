package org.jhonny.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.models.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

}
