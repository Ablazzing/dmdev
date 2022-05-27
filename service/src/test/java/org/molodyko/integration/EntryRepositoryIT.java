package org.molodyko.integration;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.Entry;
import org.molodyko.entity.User;
import org.molodyko.entity.filter.EntryFilter;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.EntryRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_ENTRY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EntryRepositoryIT extends IntegrationBase {
    private static final LocalDateTime DATE_MAX = LocalDateTime.of(2030, 1, 1, 0, 0, 0);
    private static final LocalDateTime DATE_MIN = LocalDateTime.of(2010, 1, 1, 0, 0, 0);

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EntryRepository entryRepository;

    @Test
    void create() {
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id()).orElseThrow();
        Entry entry = Entry.builder()
                .amount(BigDecimal.valueOf(1000d))
                .date(DATE_MAX)
                .operationNumber(1)
                .category(category)
                .description("some_text2").user(user).build();
        entryRepository.saveAndFlush(entry);

        Entry createdEntry = entryRepository.findById(4).orElseThrow();
        assertThat(createdEntry).isNotNull();
    }

    @Test
    void read() {
        Entry entry = entryRepository.findById(EXISTED_ENTRY_ID.id()).orElseThrow();

        assertThat(entry.getAmount().compareTo(BigDecimal.valueOf(1000d))).isEqualTo(0);
        assertThat(entry.getDescription()).isEqualTo("some_text");
        assertThat(entry.getUser().getUsername()).isEqualTo("abl");
        assertThat(entry.getCategory().getName()).isEqualTo("vacation");
        assertThat(entry.getOperationNumber()).isEqualTo(1);
        assertThat(entry.getDate()).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
    }

    @Test
    void update() {
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id()).orElseThrow();

        Entry entry = Entry.builder().id(EXISTED_ENTRY_ID.id())
                .user(user).category(category)
                .description("some_text3")
                .operationNumber(4)
                .amount(BigDecimal.valueOf(1000d))
                .date(DATE_MIN)
                .build();
        entryRepository.saveAndFlush(entry);

        Entry updatedEntry = entryRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        assertThat(updatedEntry.getOperationNumber()).isEqualTo(4);
        assertThat(updatedEntry.getDescription()).isEqualTo("some_text3");
    }

    @Test
    void delete() {
        entryRepository.deleteById(EXISTED_ENTRY_ID.id());

        Optional<Entry> deletedEntry = entryRepository.findById(EXISTED_ENTRY_ID.id());
        assertThat(deletedEntry).isEmpty();
    }

    @Test
    void checkEntryFilter() {
        EntryFilter entryFilter = EntryFilter.builder()
                .dateStart(LocalDateTime.of(2019, 1, 1, 0, 0, 0))
                .dateEnd(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .build();

        List<Entry> entries = entryRepository.getEntriesByFilter(entryFilter);

        BigDecimal expectedSum = BigDecimal.valueOf(3000d);

        assertThat(entries).hasSize(2);
        assertThat(expectedSum).isEqualTo("3000.0");
    }
}

