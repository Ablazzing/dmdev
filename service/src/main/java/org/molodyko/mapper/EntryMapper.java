package org.molodyko.mapper;

import lombok.RequiredArgsConstructor;
import org.molodyko.dto.EntryDto;
import org.molodyko.entity.Entry;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.EntryRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Integer.parseInt;


@Service
@RequiredArgsConstructor
public class EntryMapper {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EntryRepository entryRepository;

    private static LocalDateTime parseDate(EntryDto entryDto) {
        String[] date = entryDto.getDateEntry().split("\\.");
        String[] time = entryDto.getTime().split(":");
        return LocalDateTime.of(
                parseInt(date[2]),
                parseInt(date[1]),
                parseInt(date[0]),
                parseInt(time[0]),
                parseInt(time[1])
                );
    }

    private static String castDate(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return localDateTime.format(dateTimeFormatter);
    }

    private static String castTime(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return localDateTime.format(dateTimeFormatter);
    }

    public Entry convertDtoToEntity(EntryDto entryDto) {
        if (entryDto.getOperationNumber() == null) {
            Integer maxOperationForUser = entryRepository.getMaxOperationForUser(entryDto.getUsername());
            Integer nextOperationNumber = maxOperationForUser == null ? 1 : maxOperationForUser + 1;
            entryDto.setOperationNumber(nextOperationNumber);
        }

        LocalDateTime operationDateTime = parseDate(entryDto);
        BigDecimal amount = new BigDecimal(entryDto.getAmount());

        return Entry.builder()
                .id(entryDto.getId())
                .date(operationDateTime)
                .description(entryDto.getDescription())
                .amount(amount)
                .operationNumber(entryDto.getOperationNumber())
                .category(categoryRepository.findByName(entryDto.getCategoryName()).orElseThrow())
                .user(userRepository.findUserByUsername(entryDto.getUsername()).orElseThrow())
                .build();
    }


    public EntryDto convertEntityToDto(Entry entry) {
        return EntryDto.builder()
                .id(entry.getId())
                .amount(entry.getAmount().toString())
                .dateEntry(castDate(entry.getDate()))
                .time(castTime(entry.getDate()))
                .description(entry.getDescription())
                .categoryName(entry.getCategory().getName())
                .username(entry.getUser().getUsername())
                .operationNumber(entry.getOperationNumber())
                .build();
    }

}
