package org.molodyko.repository;

import org.hibernate.SessionFactory;
import org.molodyko.entity.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class HolidayRepository extends BaseRepository<Holiday, Integer> {

    @Autowired
    public HolidayRepository() {
        super(Holiday.class);
    }
}
