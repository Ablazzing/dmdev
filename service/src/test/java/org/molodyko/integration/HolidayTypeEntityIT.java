package org.molodyko.integration;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

public class HolidayTypeEntityIT extends IntegrationBase {

    @Test
    public void createHolidayType() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, EXISTED_USER_ID);
            Category category = session.get(Category.class, EXISTED_CATEGORY_ID);
            HolidayType holidayType = HolidayType.builder()
                    .name("отпуск в горы")
                    .category(category)
                    .user(user)
                    .build();
            session.save(holidayType);

            HolidayType createdHolidayType = session.get(HolidayType.class, 3);
            assertThat(createdHolidayType).isNotNull();

            session.getTransaction().commit();
        }
    }

    @Test
    public void readHolidayType() {
        try (Session session = sessionFactory.openSession()) {
            HolidayType holidayType = session.get(HolidayType.class, EXISTED_HOLIDAY_TYPE_ID);

            assertThat(holidayType.getName()).isEqualTo("отпуск на море");
            assertThat(holidayType.getUser().getUsername()).isEqualTo("abl");
            assertThat(holidayType.getCategory().getName()).isEqualTo("vacation");
        }
    }

    @Test
    public void updateHolidayType() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, EXISTED_USER_ID);
            Category category = session.get(Category.class, EXISTED_CATEGORY_ID);
            HolidayType holidayType = HolidayType.builder()
                    .user(user)
                    .category(category)
                    .id(EXISTED_HOLIDAY_TYPE_ID)
                    .name("отпуск в горы")
                    .build();
            session.update(holidayType);
            session.flush();

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteHolidayType() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            HolidayType holidayType = HolidayType.builder().id(FOR_DELETE_HOLIDAY_TYPE_ID).build();
            session.delete(holidayType);
            session.flush();

            session.getTransaction().commit();
        }
    }
}
