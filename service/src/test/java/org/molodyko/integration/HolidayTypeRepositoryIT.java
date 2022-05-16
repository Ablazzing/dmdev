package org.molodyko.integration;

import org.hibernate.Session;
import org.molodyko.entity.Category;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.HolidayTypeRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.molodyko.integration.DababaseEntityId.CREATED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_HOLIDAY_TYPE_ID;

public class HolidayTypeRepositoryIT extends IntegrationBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private HolidayTypeRepository holidayTypeRepository;

    @Override
    public void create(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id(), session);
        HolidayType holidayType = HolidayType.builder()
                .name("отпуск в горы")
                .category(category)
                .user(user)
                .build();
        holidayTypeRepository.save(holidayType, session);

        HolidayType createdHolidayType = holidayTypeRepository.findById(CREATED_HOLIDAY_TYPE_ID.id(), session);
        assertThat(createdHolidayType).isNotNull();
    }

    @Override
    public void read(Session session) {
        HolidayType holidayType = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id(), session);

        assertThat(holidayType.getName()).isEqualTo("отпуск на море");
        assertThat(holidayType.getUser().getUsername()).isEqualTo("abl");
        assertThat(holidayType.getCategory().getName()).isEqualTo("vacation");
    }

    @Override
    public void update(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id(), session);
        HolidayType holidayType = HolidayType.builder()
                .user(user)
                .category(category)
                .id(EXISTED_HOLIDAY_TYPE_ID.id())
                .name("отпуск в горы")
                .build();

        holidayTypeRepository.update(holidayType, session);

        HolidayType holidayTypeGet = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id(), session);
        assertThat(holidayTypeGet.getName()).isEqualTo(holidayType.getName());
    }

    @Override
    public void delete(Session session) {
        holidayTypeRepository.deleteById(FOR_DELETE_HOLIDAY_TYPE_ID.id(), session);

        HolidayType holidayType = holidayTypeRepository.findById(FOR_DELETE_HOLIDAY_TYPE_ID.id(), session);
        assertNull(holidayType);
    }
}
