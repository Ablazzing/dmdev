package org.molodyko.repository;

import org.molodyko.entity.User;
import org.molodyko.repository.filters.FilterUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, FilterUserRepository {

    Optional<User> findUserByUsername(String username);
    boolean existsByUsername(String name);
}
