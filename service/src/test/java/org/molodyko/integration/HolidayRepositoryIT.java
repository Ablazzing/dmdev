package org.molodyko.integration;

import org.hibernate.Session;
import org.molodyko.entity.Holiday;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;
import org.molodyko.repository.HolidayRepository;
import org.molodyko.repository.HolidayTypeRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_HOLIDAY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

public class HolidayRepositoryIT extends IntegrationBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HolidayTypeRepository holidayTypeRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    public void create(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        HolidayType holidayType = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id(), session);
        Holiday holiday = Holiday.builder()
                .startDate(LocalDate.MIN)
                .endDate(LocalDate.MAX)
                .user(user)
                .holidayType(holidayType)
                .build();
        holidayRepository.save(holiday, session);

        Holiday createdHoliday = holidayRepository.findById(CREATED_HOLIDAY_ID.id(), session);
        assertThat(createdHoliday).isNotNull();
    }

    public void read(Session session) {
        Holiday holiday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id(), session);

        LocalDate expectedStartDate = LocalDate.of(2020, 1, 1);
        LocalDate expectedEndDate = LocalDate.of(2021, 1, 1);
        assertThat(holiday.getUser().getUsername()).isEqualTo("abl");
        assertThat(holiday.getStartDate()).isEqualTo(expectedStartDate);
        assertThat(holiday.getEndDate()).isEqualTo(expectedEndDate);
        assertThat(holiday.getHolidayType().getName()).isEqualTo("отпуск на море");

    }

    public void update(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        HolidayType holidayType = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id(), session);
        Holiday holiday = Holiday.builder().id(EXISTED_HOLIDAY_ID.id())
                .user(user)
                .holidayType(holidayType)
                .startDate(LocalDate.MIN)
                .endDate(LocalDate.MAX)
                .build();
        holidayRepository.update(holiday, session);

        Holiday updatedHoliday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id(), session);
        //TODO
        //assertThat(updatedHoliday.getStartDate()).isEqualTo(LocalDate.MIN);
    }

    public void delete(Session session) {
        holidayRepository.deleteById(EXISTED_HOLIDAY_ID.id(), session);

        Holiday deletedHoliday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id(), session);
        assertThat(deletedHoliday).isNull();
    }
}
