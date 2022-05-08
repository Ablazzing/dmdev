package org.molodyko.repository;

import org.hibernate.SessionFactory;
import org.molodyko.entity.CategoryRename;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryRenameRepository extends BaseRepository<CategoryRename, Integer> {

    @Autowired
    public CategoryRenameRepository(SessionFactory sessionFactory) {
        super(CategoryRename.class);
    }
}
