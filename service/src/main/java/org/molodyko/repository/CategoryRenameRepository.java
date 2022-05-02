package org.molodyko.repository;

import org.hibernate.SessionFactory;
import org.molodyko.entity.CategoryRename;

public class CategoryRenameRepository extends BaseRepository<CategoryRename, Integer> {
    public CategoryRenameRepository(SessionFactory sessionFactory) {
        super(sessionFactory, CategoryRename.class);
    }
}
