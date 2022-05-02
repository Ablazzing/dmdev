package org.molodyko.repository;

import org.hibernate.SessionFactory;
import org.molodyko.entity.HolidayType;

public class HolidayTypeRepository extends BaseRepository<HolidayType, Integer> {
    public HolidayTypeRepository(SessionFactory sessionFactory) {
        super(sessionFactory, HolidayType.class);
    }
}
