package org.molodyko.repository;

import org.molodyko.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository extends BaseRepository<Category, Integer> {

    @Autowired
    public CategoryRepository() {
        super(Category.class);
    }
}
