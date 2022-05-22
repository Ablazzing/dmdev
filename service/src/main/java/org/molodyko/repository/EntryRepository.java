package org.molodyko.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.molodyko.entity.Entry;
import org.molodyko.entity.filter.EntryFilter;
import org.molodyko.entity.utils.QPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.molodyko.entity.QEntry.entry;

@Repository
public class EntryRepository extends BaseRepository<Entry, Integer> {

    @Autowired
    public EntryRepository() {
        super(Entry.class);
    }

    public List<Entry> getEntriesByFilter(EntryFilter entryFilter) {
        Predicate predicate = QPredicate.builder()
                .add(entryFilter.getDateStart(), entry.date::after)
                .add(entryFilter.getDateEnd(), entry.date::before)
                .andAll();

        List<Entry> entries = new JPAQuery<Entry>(entityManager)
                .select(entry)
                .from(entry)
                .where(predicate)
                .fetch();
        return entries;
    }
}
