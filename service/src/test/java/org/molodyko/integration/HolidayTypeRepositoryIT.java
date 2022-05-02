package org.molodyko.integration;

import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.HolidayTypeRepository;
import org.molodyko.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.molodyko.integration.DababaseEntityId.CREATED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_HOLIDAY_TYPE_ID;

public class HolidayTypeRepositoryIT extends IntegrationBase {
    private final UserRepository userRepository = new UserRepository(sessionFactory);
    private final CategoryRepository categoryRepository = new CategoryRepository(sessionFactory);
    private final HolidayTypeRepository holidayTypeRepository = new HolidayTypeRepository(sessionFactory);


    @Test
    public void createHolidayType() {

        User user = userRepository.findById(EXISTED_USER_ID.id());
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id());
        HolidayType holidayType = HolidayType.builder()
                .name("отпуск в горы")
                .category(category)
                .user(user)
                .build();
        holidayTypeRepository.save(holidayType);

        HolidayType createdHolidayType = holidayTypeRepository.findById(CREATED_HOLIDAY_TYPE_ID.id());
        assertThat(createdHolidayType).isNotNull();
    }

    @Test
    public void readHolidayType() {
        HolidayType holidayType = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id());

        assertThat(holidayType.getName()).isEqualTo("отпуск на море");
        assertThat(holidayType.getUser().getUsername()).isEqualTo("abl");
        assertThat(holidayType.getCategory().getName()).isEqualTo("vacation");
    }

    @Test
    public void updateHolidayType() {

        User user = userRepository.findById(EXISTED_USER_ID.id());
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id());
        HolidayType holidayType = HolidayType.builder()
                .user(user)
                .category(category)
                .id(EXISTED_HOLIDAY_TYPE_ID.id())
                .name("отпуск в горы")
                .build();

        holidayTypeRepository.update(holidayType);

        HolidayType holidayTypeGet = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id());
        assertThat(holidayTypeGet.getName()).isEqualTo(holidayType.getName());
    }

    @Test
    public void deleteHolidayType() {
        holidayTypeRepository.deleteById(FOR_DELETE_HOLIDAY_TYPE_ID.id());

        HolidayType holidayType = holidayTypeRepository.findById(FOR_DELETE_HOLIDAY_TYPE_ID.id());
        assertNull(holidayType);
    }
}
