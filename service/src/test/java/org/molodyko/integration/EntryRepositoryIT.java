package org.molodyko.integration;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.Entry;
import org.molodyko.entity.User;
import org.molodyko.entity.filter.EntryFilter;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.EntryRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_ENTRY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

public class EntryRepositoryIT extends IntegrationBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntryRepository entryRepository;

    public void create(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id(), session);
        Entry entry = Entry.builder()
                .amount(BigDecimal.valueOf(1000d))
                .date(LocalDateTime.MAX)
                .operationNumber(1)
                .category(category)
                .description("some_text2").user(user).build();
        entryRepository.save(entry, session);

        Entry createdEntry = entryRepository.findById(4, session);
        assertThat(createdEntry).isNotNull();

    }

    public void read(Session session) {
        Entry entry = entryRepository.findById(EXISTED_ENTRY_ID.id(), session);

        assertThat(entry.getAmount().compareTo(BigDecimal.valueOf(1000d))).isEqualTo(0);
        assertThat(entry.getDescription()).isEqualTo("some_text");
        assertThat(entry.getUser().getUsername()).isEqualTo("abl");
        assertThat(entry.getCategory().getName()).isEqualTo("vacation");
        assertThat(entry.getOperationNumber()).isEqualTo(1);
        assertThat(entry.getDate()).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
    }

    public void update(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id(), session);

        Entry entry = Entry.builder().id(EXISTED_ENTRY_ID.id())
                .user(user).category(category)
                .description("some_text3")
                .operationNumber(4)
                .amount(BigDecimal.valueOf(1000d))
                .date(LocalDateTime.MIN)
                .build();
        entryRepository.update(entry, session);

        Entry updatedEntry = entryRepository.findById(EXISTED_USER_ID.id(), session);
        assertThat(updatedEntry.getOperationNumber()).isEqualTo(4);
        assertThat(updatedEntry.getDescription()).isEqualTo("some_text3");
    }

    public void delete(Session session) {
        entryRepository.deleteById(EXISTED_ENTRY_ID.id(), session);

        Entry deletedEntry = entryRepository.findById(EXISTED_ENTRY_ID.id(), session);
        assertThat(deletedEntry).isNull();
    }

    @Test
    public void checkEntryFilter() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            EntryFilter entryFilter = EntryFilter.builder()
                    .dateStart(LocalDateTime.of(2019, 1, 1, 0, 0, 0))
                    .dateEnd(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                    .build();

            List<Entry> entries = entryRepository.getEntriesByFilter(entryFilter, session);
            BigDecimal entrySum = entries.stream().map(Entry::getAmount)
                    .collect(Collectors.reducing(BigDecimal.ZERO, Function.identity(), BigDecimal::add));

            BigDecimal expectedSum = BigDecimal.valueOf(3000d);

            assertThat(entries).hasSize(2);
            assertThat(entrySum.compareTo(expectedSum)).isEqualTo(0);
            session.getTransaction().commit();
        }
    }
}
