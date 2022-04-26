package org.molodyko.integration.filter;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Entry;
import org.molodyko.entity.filter.EntryFilter;
import org.molodyko.entity.utils.QPredicate;
import org.molodyko.integration.IntegrationBase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.molodyko.entity.QEntry.entry;

public class EntryFilterIT extends IntegrationBase {

    @Test
    public void checkEntryFilter() {
        try (Session session = sessionFactory.openSession()) {
            EntryFilter entryFilter = EntryFilter.builder()
                    .dateStart(LocalDateTime.of(2019, 1, 1, 0, 0, 0))
                    .dateEnd(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                    .build();
            Predicate predicate = QPredicate.builder()
                    .add(entryFilter.getDateStart(), entry.date::after)
                    .add(entryFilter.getDateEnd(), entry.date::before)
                    .andAll();

            List<Entry> entries = new JPAQuery<Entry>(session)
                    .select(entry)
                    .from(entry)
                    .where(predicate)
                    .fetch();
            BigDecimal entrySum = entries.stream().map(Entry::getAmount)
                    .collect(Collectors.reducing(BigDecimal.ZERO, Function.identity(), BigDecimal::add));

            BigDecimal expectedSum = BigDecimal.valueOf(3000d);


            Assertions.assertThat(entries).hasSize(2);
            Assertions.assertThat(entrySum.compareTo(expectedSum)).isEqualTo(0);
        }

    }
}
