package org.molodyko.integration;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CategoryEntityIT extends IntegrationBase {

    @Test
    public void createCategory() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1);
            Category food = Category.builder().name("food").user(user).build();
            session.save(food);

            session.getTransaction().commit();
        }
    }

    @Test
    public void readCategory() {
        try (Session session = sessionFactory.openSession()) {
            Category category = session.get(Category.class, 1);

            assertThat(category.getName()).isEqualTo("vacation");
            assertThat(category.getUser().getUsername()).isEqualTo("abl");
        }
    }

    @Test
    public void updateCategory() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1);
            Category category = Category.builder().id(1).name("animal").user(user).build();
            session.update(category);
            session.flush();

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteCategory() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Category category = Category.builder().id(1).build();
            session.delete(category);
            session.flush();

            session.getTransaction().commit();
        }
    }
}
