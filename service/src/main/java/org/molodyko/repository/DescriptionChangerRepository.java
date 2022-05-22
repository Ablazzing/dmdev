package org.molodyko.repository;

import org.molodyko.entity.DescriptionChanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DescriptionChangerRepository extends BaseRepository<DescriptionChanger, Integer> {

    @Autowired
    public DescriptionChangerRepository() {
        super(DescriptionChanger.class);
    }
}
