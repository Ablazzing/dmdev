package org.molodyko.repository;

import org.hibernate.SessionFactory;
import org.molodyko.entity.Holiday;

public class HolidayRepository extends BaseRepository<Holiday, Integer> {
    public HolidayRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Holiday.class);
    }
}
