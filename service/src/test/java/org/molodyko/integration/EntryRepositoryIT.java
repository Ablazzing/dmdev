package org.molodyko.integration;

import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.Entry;
import org.molodyko.entity.User;
import org.molodyko.entity.filter.EntryFilter;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.EntryRepository;
import org.molodyko.repository.UserRepository;

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
    private final UserRepository userRepository = new UserRepository(sessionFactory);
    private final CategoryRepository categoryRepository = new CategoryRepository(sessionFactory);
    private final EntryRepository entryRepository = new EntryRepository(sessionFactory);

    @Test
    public void createEntry() {

        User user = userRepository.findById(EXISTED_USER_ID.id());
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id());
        Entry entry = Entry.builder()
                .amount(BigDecimal.valueOf(1000d))
                .date(LocalDateTime.MAX)
                .operationNumber(1)
                .category(category)
                .description("some_text2").user(user).build();
        entryRepository.save(entry);

        Entry createdEntry = entryRepository.findById(4);
        assertThat(createdEntry).isNotNull();

    }

    @Test
    public void readEntry() {
        Entry entry = entryRepository.findById(EXISTED_ENTRY_ID.id());

        assertThat(entry.getAmount().compareTo(BigDecimal.valueOf(1000d))).isEqualTo(0);
        assertThat(entry.getDescription()).isEqualTo("some_text");
        assertThat(entry.getUser().getUsername()).isEqualTo("abl");
        assertThat(entry.getCategory().getName()).isEqualTo("vacation");
        assertThat(entry.getOperationNumber()).isEqualTo(1);
        assertThat(entry.getDate()).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
    }

    @Test
    public void updateEntry() {
        User user = userRepository.findById(EXISTED_USER_ID.id());
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id());

        Entry entry = Entry.builder().id(EXISTED_ENTRY_ID.id())
                .user(user).category(category)
                .description("some_text3")
                .operationNumber(4)
                .amount(BigDecimal.valueOf(1000d))
                .date(LocalDateTime.MIN)
                .build();
        entryRepository.update(entry);

        Entry updatedEntry = entryRepository.findById(EXISTED_USER_ID.id());
        assertThat(updatedEntry.getOperationNumber()).isEqualTo(4);
        assertThat(updatedEntry.getDescription()).isEqualTo("some_text3");
    }

    @Test
    public void deleteEntry() {
        entryRepository.deleteById(EXISTED_ENTRY_ID.id());

        Entry deletedEntry = entryRepository.findById(EXISTED_ENTRY_ID.id());
        assertThat(deletedEntry).isNull();
    }

    @Test
    public void checkEntryFilter() {
        EntryFilter entryFilter = EntryFilter.builder()
                .dateStart(LocalDateTime.of(2019, 1, 1, 0, 0, 0))
                .dateEnd(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .build();

        List<Entry> entries = entryRepository.getEntriesByFilter(entryFilter);
        BigDecimal entrySum = entries.stream().map(Entry::getAmount)
                .collect(Collectors.reducing(BigDecimal.ZERO, Function.identity(), BigDecimal::add));

        BigDecimal expectedSum = BigDecimal.valueOf(3000d);

        assertThat(entries).hasSize(2);
        assertThat(entrySum.compareTo(expectedSum)).isEqualTo(0);
    }
}
