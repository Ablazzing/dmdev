package org.molodyko.integration;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.Entry;
import org.molodyko.entity.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class EntryEntityIT extends IntegrationBase {

    @Test
    public void createEntry() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1);
            Category category = session.get(Category.class, 1);
            Entry entry = Entry.builder()
                    .amount(1000d)
                    .date(LocalDateTime.MAX)
                    .operationNumber(1)
                    .category(category)
                    .description("some_text2")
                    .user(user)
                    .build();
            session.save(entry);

            session.getTransaction().commit();
        }
    }

    @Test
    public void readEntry() {
        try (Session session = sessionFactory.openSession()) {
            Entry entry = session.get(Entry.class, 1);

            assertThat(entry.getAmount()).isEqualTo(1000);
            assertThat(entry.getDescription()).isEqualTo("some_text");
            assertThat(entry.getUser().getUsername()).isEqualTo("abl");
            assertThat(entry.getCategory().getName()).isEqualTo("vacation");
            assertThat(entry.getOperationNumber()).isEqualTo(1);
            assertThat(entry.getDate()).isEqualTo(
                    LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        }
    }

    @Test
    public void updateEntry() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, 1);
            Category category = session.get(Category.class, 1);

            Entry entry = Entry.builder()
                    .id(1)
                    .user(user)
                    .category(category)
                    .description("some_text3")
                    .operationNumber(1)
                    .amount(1000d)
                    .date(LocalDateTime.MIN)
                    .build();
            session.update(entry);
            session.flush();

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteEntry() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Entry entry = Entry.builder().id(1).build();
            session.delete(entry);
            session.flush();

            session.getTransaction().commit();
        }
    }
}
