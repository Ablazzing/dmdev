package org.molodyko.integration;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.HolidayTypeRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_HOLIDAY_TYPE_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_HOLIDAY_TYPE_ID;

@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HolidayTypeRepositoryIT extends IntegrationBase {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final HolidayTypeRepository holidayTypeRepository;

    @Test
    void create() {
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id()).orElseThrow();
        HolidayType holidayType = HolidayType.builder()
                .name("отпуск в горы")
                .category(category)
                .user(user)
                .build();
        holidayTypeRepository.saveAndFlush(holidayType);

        HolidayType createdHolidayType = holidayTypeRepository.findById(CREATED_HOLIDAY_TYPE_ID.id()).orElseThrow();
        assertThat(createdHolidayType).isNotNull();
    }

    @Test
    void read() {
        HolidayType holidayType = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id()).orElseThrow();

        assertThat(holidayType.getName()).isEqualTo("отпуск на море");
        assertThat(holidayType.getUser().getUsername()).isEqualTo("abl");
        assertThat(holidayType.getCategory().getName()).isEqualTo("vacation");
    }

    @Test
    void update() {
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id()).orElseThrow();
        HolidayType holidayType = HolidayType.builder()
                .user(user)
                .category(category)
                .id(EXISTED_HOLIDAY_TYPE_ID.id())
                .name("отпуск в горы")
                .build();

        holidayTypeRepository.saveAndFlush(holidayType);

        HolidayType holidayTypeGet = holidayTypeRepository.findById(EXISTED_HOLIDAY_TYPE_ID.id()).orElseThrow();
        assertThat(holidayTypeGet.getName()).isEqualTo(holidayType.getName());
    }

    @Test
    void delete() {
        holidayTypeRepository.deleteById(FOR_DELETE_HOLIDAY_TYPE_ID.id());

        Optional<HolidayType> holidayType = holidayTypeRepository.findById(FOR_DELETE_HOLIDAY_TYPE_ID.id());
        assertThat(holidayType).isEmpty();
    }
}
