package org.molodyko.integration;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_CATEGORY_ID;

public class CategoryEntityIT extends IntegrationBase {

    @Test
    public void createCategory() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, EXISTED_USER_ID.id());
            Category food = Category.builder().name("food").user(user).build();
            session.save(food);

            Category createdCategory = session.get(Category.class, CREATED_CATEGORY_ID.id());
            assertThat(createdCategory).isNotNull();

            session.getTransaction().commit();
        }
    }

    @Test
    public void readCategory() {
        try (Session session = sessionFactory.openSession()) {
            Category category = session.get(Category.class, EXISTED_CATEGORY_ID.id());

            assertThat(category.getName()).isEqualTo("vacation");
            assertThat(category.getUser().getUsername()).isEqualTo("abl");
        }
    }

    @Test
    public void updateCategory() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, EXISTED_USER_ID.id());
            Category category = Category.builder().id(EXISTED_CATEGORY_ID.id()).name("animal").user(user).build();
            session.update(category);
            session.flush();

            Category updatedCategory = session.get(Category.class, EXISTED_CATEGORY_ID.id());
            assertThat(updatedCategory.getName()).isEqualTo("animal");

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteCategory() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Category category = Category.builder().id(FOR_DELETE_CATEGORY_ID.id()).build();
            session.delete(category);
            session.flush();

            Category deletedCategory = session.get(Category.class, FOR_DELETE_CATEGORY_ID.id());
            assertThat(deletedCategory).isNull();

            session.getTransaction().commit();
        }
    }
}
