package org.jhonny.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UserRepository extends EntityRepository<Users> {

}
