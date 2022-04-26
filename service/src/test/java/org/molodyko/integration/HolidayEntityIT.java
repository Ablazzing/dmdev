package org.molodyko.integration;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Holiday;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_HOLIDAY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

public class HolidayEntityIT extends IntegrationBase {

    @Test
    public void createHoliday() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, EXISTED_USER_ID.id());
            HolidayType holidayType = session.get(HolidayType.class, EXISTED_HOLIDAY_TYPE_ID.id());
            Holiday holiday = Holiday.builder()
                    .startDate(LocalDate.MIN)
                    .endDate(LocalDate.MAX)
                    .user(user)
                    .holidayType(holidayType)
                    .build();
            session.save(holiday);

            Holiday createdHoliday = session.get(Holiday.class, CREATED_HOLIDAY_ID.id());
            assertThat(createdHoliday).isNotNull();

            session.getTransaction().commit();
        }
    }

    @Test
    public void readHoliday() {
        try (Session session = sessionFactory.openSession()) {
            Holiday holiday = session.get(Holiday.class, EXISTED_HOLIDAY_ID.id());

            LocalDate expectedStartDate = LocalDate.of(2020, 1, 1);
            LocalDate expectedEndDate = LocalDate.of(2021, 1, 1);
            assertThat(holiday.getUser().getUsername()).isEqualTo("abl");
            assertThat(holiday.getStartDate()).isEqualTo(expectedStartDate);
            assertThat(holiday.getEndDate()).isEqualTo(expectedEndDate);
            assertThat(holiday.getHolidayType().getName()).isEqualTo("отпуск на море");

        }
    }

    @Test
    public void updateHoliday() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, EXISTED_USER_ID.id());
            HolidayType holidayType = session.get(HolidayType.class, EXISTED_HOLIDAY_TYPE_ID.id());
            Holiday holiday = Holiday.builder().id(EXISTED_HOLIDAY_ID.id())
                    .user(user)
                    .holidayType(holidayType)
                    .startDate(LocalDate.MIN)
                    .endDate(LocalDate.MAX)
                    .build();
            session.update(holiday);
            session.flush();

            Holiday updatedHoliday = session.get(Holiday.class, EXISTED_HOLIDAY_ID.id());
            assertThat(updatedHoliday.getStartDate()).isEqualTo(LocalDate.MIN);

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteHoliday() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Holiday holiday = Holiday.builder().id(EXISTED_HOLIDAY_ID.id()).build();
            session.delete(holiday);
            session.flush();

            Holiday deletedHoliday = session.get(Holiday.class, EXISTED_HOLIDAY_ID.id());
            assertThat(deletedHoliday).isNull();

            session.getTransaction().commit();
        }
    }
}
