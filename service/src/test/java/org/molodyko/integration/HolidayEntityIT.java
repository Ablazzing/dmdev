package org.molodyko.integration;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Holiday;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class HolidayEntityIT extends IntegrationBase {

    @Test
    public void createHoliday() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1);
            HolidayType holidayType = session.get(HolidayType.class, 1);
            Holiday holiday = Holiday.builder()
                    .startDate(LocalDate.MIN)
                    .endDate(LocalDate.MAX)
                    .user(user)
                    .holidayType(holidayType)
                    .build();
            session.save(holiday);

            session.getTransaction().commit();
        }
    }

    @Test
    public void readHoliday() {
        try (Session session = sessionFactory.openSession()) {
            Holiday holiday = session.get(Holiday.class, 1);

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

            User user = session.get(User.class, 1);
            HolidayType holidayType = session.get(HolidayType.class, 1);
            Holiday holiday = Holiday.builder()
                    .id(1)
                    .user(user)
                    .holidayType(holidayType)
                    .startDate(LocalDate.MIN)
                    .endDate(LocalDate.MAX)
                    .build();
            session.update(holiday);
            session.flush();

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteHoliday() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Holiday holiday = Holiday.builder().id(1).build();
            session.delete(holiday);
            session.flush();

            session.getTransaction().commit();
        }
    }
}
