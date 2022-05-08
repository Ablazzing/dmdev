package org.molodyko.repository;

import org.hibernate.SessionFactory;
import org.molodyko.entity.HolidayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HolidayTypeRepository extends BaseRepository<HolidayType, Integer> {

    @Autowired
    public HolidayTypeRepository(SessionFactory sessionFactory) {
        super(HolidayType.class);
    }
}
