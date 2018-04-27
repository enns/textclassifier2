package org.ripreal.textclassifier2.gateway.repository;

import org.ripreal.textclassifier2.gateway.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    Optional<User
            > findOneByUsername(String userName);
}
