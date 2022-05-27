package org.molodyko.repository;

import org.molodyko.entity.Entry;
import org.molodyko.repository.filters.EntryFilterRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Integer>, EntryFilterRepository {

}
