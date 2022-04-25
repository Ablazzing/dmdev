package org.molodyko.integration;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.DescriptionChanger;
import org.molodyko.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

public class DescriptionChangerEntityIT extends IntegrationBase {

    @Test
    public void createDescriptionChanger() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, EXISTED_USER_ID);
            Category category = session.get(Category.class, EXISTED_CATEGORY_ID);

            DescriptionChanger descriptionChanger = DescriptionChanger.builder().user(user).category(category).descriptionPattern("some_text2").build();
            session.save(descriptionChanger);

            DescriptionChanger changer = session.get(DescriptionChanger.class, 2);
            assertThat(changer).isNotNull();

            session.getTransaction().commit();
        }
    }

    @Test
    public void readDescriptionChanger() {
        try (Session session = sessionFactory.openSession()) {
            DescriptionChanger descriptionChanger = session.get(DescriptionChanger.class, EXISTED_DESCRIPTION_CHANGER_ID);

            assertThat(descriptionChanger.getDescriptionPattern()).isEqualTo("some_text");
            assertThat(descriptionChanger.getUser().getUsername()).isEqualTo("abl");
            assertThat(descriptionChanger.getCategory().getName()).isEqualTo("vacation");
        }
    }

    @Test
    public void updateDescriptionChanger() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, EXISTED_USER_ID);
            Category category = session.get(Category.class, EXISTED_CATEGORY_ID);
            DescriptionChanger changer = DescriptionChanger.builder().user(user).category(category).descriptionPattern("new pattern").id(EXISTED_DESCRIPTION_CHANGER_ID).build();
            session.update(changer);
            session.flush();

            DescriptionChanger updatedChanger = session.get(DescriptionChanger.class, EXISTED_DESCRIPTION_CHANGER_ID);
            assertThat(updatedChanger.getDescriptionPattern()).isEqualTo("new pattern");

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteDescriptionChanger() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            DescriptionChanger changer = DescriptionChanger.builder().id(EXISTED_DESCRIPTION_CHANGER_ID).build();
            session.delete(changer);
            session.flush();

            DescriptionChanger deletedChanger = session.get(DescriptionChanger.class, EXISTED_DESCRIPTION_CHANGER_ID);
            assertThat(deletedChanger).isNull();

            session.getTransaction().commit();
        }
    }
}