package org.molodyko.integration;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class HolidayTypeEntityIT extends IntegrationBase {

    @Test
    public void createHolidayType() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1);
            Category category = session.get(Category.class, 1);
            HolidayType holidayType = HolidayType.builder().name("отпуск в горы").category(category).user(user).build();
            session.save(holidayType);

            session.getTransaction().commit();
        }
    }

    @Test
    public void readHolidayType() {
        try (Session session = sessionFactory.openSession()) {
            HolidayType holidayType = session.get(HolidayType.class, 1);

            assertThat(holidayType.getName()).isEqualTo("отпуск на море");
            assertThat(holidayType.getUser().getUsername()).isEqualTo("abl");
            assertThat(holidayType.getCategory().getName()).isEqualTo("vacation");
        }
    }

    @Test
    public void updateHolidayType() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1);
            Category category = session.get(Category.class, 1);
            HolidayType holidayType = HolidayType.builder().user(user).category(category).id(1).name("отпуск в горы").build();
            session.update(holidayType);
            session.flush();

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteHolidayType() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            HolidayType holidayType = HolidayType.builder().id(1).build();
            session.delete(holidayType);
            session.flush();

            session.getTransaction().commit();
        }
    }
}
