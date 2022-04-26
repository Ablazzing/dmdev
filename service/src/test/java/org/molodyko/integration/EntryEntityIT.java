package org.molodyko.integration;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.Entry;
import org.molodyko.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_ENTRY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

public class EntryEntityIT extends IntegrationBase {

    @Test
    public void createEntry() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, EXISTED_USER_ID.id());
            Category category = session.get(Category.class, EXISTED_CATEGORY_ID.id());
            Entry entry = Entry.builder()
                    .amount(BigDecimal.valueOf(1000d))
                    .date(LocalDateTime.MAX)
                    .operationNumber(1)
                    .category(category)
                    .description("some_text2").user(user).build();
            session.save(entry);

            Entry createdEntry = session.get(Entry.class, 4);
            assertThat(createdEntry).isNotNull();

            session.getTransaction().commit();
        }
    }

    @Test
    public void readEntry() {
        try (Session session = sessionFactory.openSession()) {
            Entry entry = session.get(Entry.class, EXISTED_ENTRY_ID.id());

            assertThat(entry.getAmount().compareTo(BigDecimal.valueOf(1000d))).isEqualTo(0);
            assertThat(entry.getDescription()).isEqualTo("some_text");
            assertThat(entry.getUser().getUsername()).isEqualTo("abl");
            assertThat(entry.getCategory().getName()).isEqualTo("vacation");
            assertThat(entry.getOperationNumber()).isEqualTo(1);
            assertThat(entry.getDate()).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        }
    }

    @Test
    public void updateEntry() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, EXISTED_USER_ID.id());
            Category category = session.get(Category.class, EXISTED_CATEGORY_ID.id());

            Entry entry = Entry.builder().id(EXISTED_ENTRY_ID.id())
                    .user(user).category(category)
                    .description("some_text3")
                    .operationNumber(4)
                    .amount(BigDecimal.valueOf(1000d))
                    .date(LocalDateTime.MIN)
                    .build();
            session.update(entry);
            session.flush();

            Entry updatedEntry = session.get(Entry.class, EXISTED_USER_ID.id());
            assertThat(updatedEntry.getOperationNumber()).isEqualTo(4);
            assertThat(updatedEntry.getDescription()).isEqualTo("some_text3");

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteEntry() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Entry entry = Entry.builder().id(EXISTED_ENTRY_ID.id()).build();
            session.delete(entry);
            session.flush();

            Entry deletedEntry = session.get(Entry.class, EXISTED_ENTRY_ID.id());
            assertThat(deletedEntry).isNull();

            session.getTransaction().commit();
        }
    }
}
