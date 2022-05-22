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

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_HOLIDAY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HolidayRepositoryIT extends IntegrationBase {

    private final UserRepository userRepository;
    private final HolidayTypeRepository holidayTypeRepository;
    private final HolidayRepository holidayRepository;

    @Test
    public void create() {
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
    public void read() {
        Holiday holiday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id());

        LocalDate expectedStartDate = LocalDate.of(2020, 1, 1);
        LocalDate expectedEndDate = LocalDate.of(2021, 1, 1);
        assertThat(holiday.getUser().getUsername()).isEqualTo("abl");
        assertThat(holiday.getStartDate()).isEqualTo(expectedStartDate);
        assertThat(holiday.getEndDate()).isEqualTo(expectedEndDate);
        assertThat(holiday.getHolidayType().getName()).isEqualTo("отпуск на море");

    }

    @Test
    public void update() {
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
    public void delete() {
        holidayRepository.deleteById(EXISTED_HOLIDAY_ID.id());

        Holiday deletedHoliday = holidayRepository.findById(EXISTED_HOLIDAY_ID.id());
        assertThat(deletedHoliday).isNull();
    }
}
