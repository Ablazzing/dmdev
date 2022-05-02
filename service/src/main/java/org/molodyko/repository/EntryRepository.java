package org.molodyko.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.molodyko.entity.Entry;
import org.molodyko.entity.filter.EntryFilter;
import org.molodyko.entity.utils.QPredicate;

import java.util.List;

import static org.molodyko.entity.QEntry.entry;

public class EntryRepository extends BaseRepository<Entry, Integer> {

    public EntryRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Entry.class);
    }

    public List<Entry> getEntriesByFilter(EntryFilter entryFilter) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Predicate predicate = QPredicate.builder()
                .add(entryFilter.getDateStart(), entry.date::after)
                .add(entryFilter.getDateEnd(), entry.date::before)
                .andAll();

        List<Entry> entries = new JPAQuery<Entry>(session)
                .select(entry)
                .from(entry)
                .where(predicate)
                .fetch();
        session.getTransaction().commit();
        return entries;
    }
}
