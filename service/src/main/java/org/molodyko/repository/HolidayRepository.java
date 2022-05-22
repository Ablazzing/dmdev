package org.molodyko.repository;

import org.molodyko.entity.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HolidayRepository extends BaseRepository<Holiday, Integer> {

    @Autowired
    public HolidayRepository() {
        super(Holiday.class);
    }
}
