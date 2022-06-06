package org.molodyko.repository;

import org.molodyko.entity.Entry;
import org.molodyko.repository.filters.EntryFilterRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntryRepository extends JpaRepository<Entry, Integer>, EntryFilterRepository {

    @Query(
            value = "SELECT MAX(e.operation_number) as max_op " +
                    "FROM entry e JOIN users u on e.user_id = u.id " +
                    "WHERE u.username = ?1",
            nativeQuery = true)
    Integer getMaxOperationForUser(String username);
}
