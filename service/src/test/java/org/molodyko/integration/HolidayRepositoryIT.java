package org.molodyko.integration;

import org.junit.jupiter.api.Test;
import org.molodyko.entity.Holiday;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;
import org.molodyko.repository.HolidayRepository;
import org.molodyko.repository.HolidayTypeRepository;
import org.molodyko.repository.UserRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_HOLIDAY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

public class HolidayRepositoryIT extends IntegrationBase {
    private final UserRepository userRepository = new UserRepository(sessionFactory);
    private final HolidayTypeRepository holidayTypeRepository = new HolidayTypeRepository(sessionFactory);
    private final HolidayRepository holidayRepository = new HolidayRepository(sessionFactory);

    @Test
    public void createHoliday() {

        User user = userRepository.findById(EXISTED_USER_ID.id());
        HolidayType holidayType = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id());
        Holiday holiday = Holiday.builder()
                .startDate(LocalDate.MIN)
                .endDate(LocalDate.MAX)
                .user(user)
                .holidayType(holidayType)
                .build();
        holidayRepository.save(holiday);

        Holiday createdHoliday = holidayRepository.findById(CREATED_HOLIDAY_ID.id());
        assertThat(createdHoliday).isNotNull();
    }

    @Test
    public void readHoliday() {
        Holiday holiday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id());

        LocalDate expectedStartDate = LocalDate.of(2020, 1, 1);
        LocalDate expectedEndDate = LocalDate.of(2021, 1, 1);
        assertThat(holiday.getUser().getUsername()).isEqualTo("abl");
        assertThat(holiday.getStartDate()).isEqualTo(expectedStartDate);
        assertThat(holiday.getEndDate()).isEqualTo(expectedEndDate);
        assertThat(holiday.getHolidayType().getName()).isEqualTo("отпуск на море");

    }

    @Test
    public void updateHoliday() {

        User user = userRepository.findById(EXISTED_USER_ID.id());
        HolidayType holidayType = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id());
        Holiday holiday = Holiday.builder().id(EXISTED_HOLIDAY_ID.id())
                .user(user)
                .holidayType(holidayType)
                .startDate(LocalDate.MIN)
                .endDate(LocalDate.MAX)
                .build();
        holidayRepository.update(holiday);

        Holiday updatedHoliday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id());
        //TODO
        //assertThat(updatedHoliday.getStartDate()).isEqualTo(LocalDate.MIN);
    }

    @Test
    public void deleteHoliday() {

        holidayRepository.deleteById(EXISTED_HOLIDAY_ID.id());

        Holiday deletedHoliday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id());
        assertThat(deletedHoliday).isNull();
    }
}
