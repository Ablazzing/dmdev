package org.molodyko.repository;

import org.molodyko.entity.Entry;
import org.molodyko.entity.filter.EntryFilter;
import org.molodyko.repository.filters.EntryFilterRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Integer>, EntryFilterRepository {

     List<Entry> getEntriesByFilter(EntryFilter entryFilter);
}
