package org.molodyko.repository;

import org.molodyko.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);
    boolean existsByNameAndUser_Username(String name, String username);

}
