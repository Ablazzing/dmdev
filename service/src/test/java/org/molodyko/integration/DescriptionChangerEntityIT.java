package org.molodyko.integration;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.DescriptionChanger;
import org.molodyko.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class DescriptionChangerEntityIT extends IntegrationBase {

    @Test
    public void createDescriptionChanger() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1);
            Category category = session.get(Category.class, 1);

            DescriptionChanger descriptionChanger = DescriptionChanger.builder()
                    .user(user)
                    .category(category)
                    .descriptionPattern("some_text2")
                    .build();
            session.save(descriptionChanger);

            session.getTransaction().commit();
        }
    }

    @Test
    public void readDescriptionChanger() {
        try (Session session = sessionFactory.openSession()) {
            DescriptionChanger descriptionChanger = session.get(DescriptionChanger.class, 1);

            assertThat(descriptionChanger.getDescriptionPattern()).isEqualTo("some_text");
            assertThat(descriptionChanger.getUser().getUsername()).isEqualTo("abl");
            assertThat(descriptionChanger.getCategory().getName()).isEqualTo("vacation");
        }
    }

    @Test
    public void updateDescriptionChanger() {
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
    public void deleteDescriptionChanger() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Category category = Category.builder().id(1).build();
            session.delete(category);
            session.flush();

            session.getTransaction().commit();
        }
    }
}
