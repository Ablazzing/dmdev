package org.molodyko.repository.filters;

import org.molodyko.entity.Entry;
import org.molodyko.entity.filter.EntryFilter;

import java.util.List;

public interface EntryFilterRepository {
    List<Entry> getEntriesByFilter(EntryFilter entryFilter);
}
