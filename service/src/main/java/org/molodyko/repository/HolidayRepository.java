package org.molodyko.repository;

import org.hibernate.SessionFactory;
import org.molodyko.entity.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HolidayRepository extends BaseRepository<Holiday, Integer> {

    @Autowired
    public HolidayRepository(SessionFactory sessionFactory) {
        super(Holiday.class);
    }
}
