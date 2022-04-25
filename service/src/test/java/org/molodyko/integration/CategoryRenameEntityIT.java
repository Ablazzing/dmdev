package org.molodyko.integration;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.CategoryRename;
import org.molodyko.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryRenameEntityIT extends IntegrationBase {

    @Test
    public void createCategoryRename() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Category categoryBefore = session.get(Category.class, EXISTED_CATEGORY_ID);
            Category categoryAfter = session.get(Category.class, EXISTED_CATEGORY_ANOTHER_ID);
            User user = session.get(User.class, EXISTED_USER_ID);
            CategoryRename categoryRename = CategoryRename.builder()
                    .categoryAfter(categoryAfter)
                    .categoryBefore(categoryBefore)
                    .user(user)
                    .build();
            session.save(categoryRename);

            CategoryRename createdRenamer = session.get(CategoryRename.class, 2);
            assertThat(createdRenamer).isNotNull();

            session.getTransaction().commit();
        }
    }

    @Test
    public void readCategoryRename() {
        try (Session session = sessionFactory.openSession()) {
            CategoryRename categoryRename = session.get(CategoryRename.class, EXISTED_CATEGORY_RENAME_ID);

            assertThat(categoryRename.getCategoryBefore().getName()).isEqualTo("vacation");
            assertThat(categoryRename.getCategoryAfter().getName()).isEqualTo("car");
            assertThat(categoryRename.getUser().getUsername()).isEqualTo("abl");
        }
    }

    @Test
    public void updateCategoryRename() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, EXISTED_USER_ID);
            Category categoryBefore = session.get(Category.class, EXISTED_CATEGORY_ANOTHER_ID);
            Category categoryAfter = session.get(Category.class, FOR_DELETE_CATEGORY_ID);
            CategoryRename categoryRename = CategoryRename.builder()
                    .id(EXISTED_CATEGORY_RENAME_ID)
                    .user(user)
                    .categoryBefore(categoryBefore)
                    .categoryAfter(categoryAfter)
                    .build();

            session.update(categoryRename);
            session.flush();

            CategoryRename updatedRenamer = session.get(CategoryRename.class, EXISTED_CATEGORY_RENAME_ID);
            assertThat(updatedRenamer.getCategoryAfter().getId()).isEqualTo(FOR_DELETE_CATEGORY_ID);

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteCategoryRename() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CategoryRename categoryRename = CategoryRename.builder().id(EXISTED_CATEGORY_RENAME_ID).build();
            session.delete(categoryRename);
            session.flush();

            CategoryRename deletedRenamer = session.get(CategoryRename.class, EXISTED_CATEGORY_RENAME_ID);
            assertThat(deletedRenamer).isNull();

            session.getTransaction().commit();
        }
    }
}
