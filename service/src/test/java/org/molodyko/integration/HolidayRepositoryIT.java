package org.molodyko.integration;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Holiday;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;
import org.molodyko.repository.HolidayRepository;
import org.molodyko.repository.HolidayTypeRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_HOLIDAY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HolidayRepositoryIT extends IntegrationBase {
    private static final LocalDateTime DATE_MAX = LocalDateTime.of(2030, 1, 1, 0, 0, 0);
    private static final LocalDateTime DATE_MIN = LocalDateTime.of(2010, 1, 1, 0, 0, 0);


    private final UserRepository userRepository;
    private final HolidayTypeRepository holidayTypeRepository;
    private final HolidayRepository holidayRepository;

    @Test
    public void create() {
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        HolidayType holidayType = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id()).orElseThrow();
        Holiday holiday = Holiday.builder()
                .startDate(DATE_MIN.toLocalDate())
                .endDate(DATE_MAX.toLocalDate())
                .user(user)
                .holidayType(holidayType)
                .build();
        holidayRepository.saveAndFlush(holiday);

        Holiday createdHoliday = holidayRepository.findById(CREATED_HOLIDAY_ID.id()).orElseThrow();
        assertThat(createdHoliday).isNotNull();
    }

    @Test
    public void read() {
        Holiday holiday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id()).orElseThrow();

        LocalDate expectedStartDate = LocalDate.of(2020, 1, 1);
        LocalDate expectedEndDate = LocalDate.of(2021, 1, 1);
        assertThat(holiday.getUser().getUsername()).isEqualTo("abl");
        assertThat(holiday.getStartDate()).isEqualTo(expectedStartDate);
        assertThat(holiday.getEndDate()).isEqualTo(expectedEndDate);
        assertThat(holiday.getHolidayType().getName()).isEqualTo("отпуск на море");

    }

    @Test
    public void update() {
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        HolidayType holidayType = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id()).orElseThrow();
        Holiday holiday = Holiday.builder().id(EXISTED_HOLIDAY_ID.id())
                .user(user)
                .holidayType(holidayType)
                .startDate(DATE_MIN.toLocalDate())
                .endDate(DATE_MAX.toLocalDate())
                .build();
        holidayRepository.saveAndFlush(holiday);

        Holiday updatedHoliday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id()).orElseThrow();
        //TODO
        //assertThat(updatedHoliday.getStartDate()).isEqualTo(LocalDate.MIN);
    }

    @Test
    public void delete() {
        holidayRepository.deleteById(EXISTED_HOLIDAY_ID.id());

        Optional<Holiday> deletedHoliday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id());
        assertThat(deletedHoliday).isEmpty();
    }
}
