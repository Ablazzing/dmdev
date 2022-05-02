package org.molodyko.repository;

import org.hibernate.SessionFactory;
import org.molodyko.entity.Category;

public class CategoryRepository extends BaseRepository<Category, Integer> {

    public CategoryRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Category.class);
    }
}
