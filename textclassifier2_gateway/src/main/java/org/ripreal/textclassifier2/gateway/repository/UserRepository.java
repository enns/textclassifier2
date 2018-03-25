package org.ripreal.textclassifier2.gateway.repository;

import org.ripreal.textclassifier2.gateway.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
