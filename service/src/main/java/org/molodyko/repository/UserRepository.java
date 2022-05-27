package org.molodyko.repository;

import org.molodyko.entity.User;
import org.molodyko.repository.filters.FilterUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>, FilterUserRepository {
}
