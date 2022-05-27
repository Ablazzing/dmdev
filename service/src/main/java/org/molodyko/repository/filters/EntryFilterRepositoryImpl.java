package org.molodyko.repository.filters;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.molodyko.entity.Entry;
import org.molodyko.entity.filter.EntryFilter;
import org.molodyko.entity.utils.QPredicate;

import javax.persistence.EntityManager;
import java.util.List;

import static org.molodyko.entity.QEntry.entry;

@RequiredArgsConstructor
public class EntryFilterRepositoryImpl implements EntryFilterRepository {
    private final EntityManager entityManager;

    @Override
    public List<Entry> getEntriesByFilter(EntryFilter entryFilter) {
        Predicate predicate = QPredicate.builder()
                .add(entryFilter.getDateStart(), entry.date::after)
                .add(entryFilter.getDateEnd(), entry.date::before)
                .andAll();

        return new JPAQuery<Entry>(entityManager)
                .select(entry)
                .from(entry)
                .where(predicate)
                .fetch();
    }
}
