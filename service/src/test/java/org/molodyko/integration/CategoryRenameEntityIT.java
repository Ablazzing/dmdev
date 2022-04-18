package org.molodyko.integration;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.CategoryRename;
import org.molodyko.entity.User;

@Slf4j
public class CategoryRenameEntityIT extends IntegrationBase {

    @Test
    public void createCategoryRename() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Category categoryBefore = session.get(Category.class, 1);
            Category categoryAfter = session.get(Category.class, 2);
            User user = session.get(User.class, 1);
            CategoryRename categoryRename = CategoryRename.builder()
                    .categoryAfter(categoryAfter)
                    .categoryBefore(categoryBefore)
                    .user(user)
                    .build();
            session.save(categoryRename);

            session.getTransaction().commit();
        }
    }

    @Test
    public void readCategoryRename() {
        try (Session session = sessionFactory.openSession()) {
            CategoryRename categoryRename = session.get(CategoryRename.class, 1);

            Assertions.assertThat(categoryRename.getCategoryBefore().getName()).isEqualTo("vacation");
            Assertions.assertThat(categoryRename.getCategoryAfter().getName()).isEqualTo("car");
            Assertions.assertThat(categoryRename.getUser().getUsername()).isEqualTo("abl");
        }
    }

    @Test
    public void updateCategoryRename() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1);
            Category categoryBefore = session.get(Category.class, 1);
            Category categoryAfter = session.get(Category.class, 3);
            CategoryRename categoryRename = CategoryRename.builder()
                    .id(1)
                    .user(user)
                    .categoryBefore(categoryBefore)
                    .categoryAfter(categoryAfter)
                    .build();

            session.update(categoryRename);
            session.flush();

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteCategoryRename() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CategoryRename categoryRename = CategoryRename.builder().id(1).build();
            session.delete(categoryRename);
            session.flush();

            session.getTransaction().commit();
        }
    }
}
