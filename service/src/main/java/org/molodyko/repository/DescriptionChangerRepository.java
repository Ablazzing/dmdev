package org.molodyko.repository;

import org.hibernate.SessionFactory;
import org.molodyko.entity.DescriptionChanger;

public class DescriptionChangerRepository extends BaseRepository<DescriptionChanger, Integer> {
    public DescriptionChangerRepository(SessionFactory sessionFactory) {
        super(sessionFactory, DescriptionChanger.class);
    }
}
