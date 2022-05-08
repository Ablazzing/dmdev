package org.molodyko.repository;

import org.hibernate.SessionFactory;
import org.molodyko.entity.DescriptionChanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DescriptionChangerRepository extends BaseRepository<DescriptionChanger, Integer> {

    @Autowired
    public DescriptionChangerRepository(SessionFactory sessionFactory) {
        super(DescriptionChanger.class);
    }
}
