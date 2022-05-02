package org.molodyko.integration;

import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.DescriptionChanger;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.DescriptionChangerRepository;
import org.molodyko.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_DESCRIPTION_CHANGER_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_DESCRIPTION_CHANGER_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

public class DescriptionChangerRepositoryIT extends IntegrationBase {
    private final UserRepository userRepository = new UserRepository(sessionFactory);
    private final CategoryRepository categoryRepository = new CategoryRepository(sessionFactory);
    private final DescriptionChangerRepository descrRepository = new DescriptionChangerRepository(sessionFactory);

    @Test
    public void createDescriptionChanger() {
        User user = userRepository.findById(EXISTED_USER_ID.id());
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id());

        DescriptionChanger descriptionChanger = DescriptionChanger.builder()
                .user(user)
                .category(category)
                .descriptionPattern("some_text2")
                .build();
        descrRepository.save(descriptionChanger);

        DescriptionChanger changer = descrRepository.findById(CREATED_DESCRIPTION_CHANGER_ID.id());
        assertThat(changer).isNotNull();
    }

    @Test
    public void readDescriptionChanger() {
        DescriptionChanger descriptionChanger = descrRepository.findById(EXISTED_DESCRIPTION_CHANGER_ID.id());

        assertThat(descriptionChanger.getDescriptionPattern()).isEqualTo("some_text");
        assertThat(descriptionChanger.getUser().getUsername()).isEqualTo("abl");
        assertThat(descriptionChanger.getCategory().getName()).isEqualTo("vacation");
    }

    @Test
    public void updateDescriptionChanger() {
        User user = userRepository.findById(EXISTED_USER_ID.id());
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id());
        DescriptionChanger changer = DescriptionChanger.builder()
                .user(user)
                .category(category)
                .descriptionPattern("new pattern")
                .id(EXISTED_DESCRIPTION_CHANGER_ID.id()).build();
        descrRepository.update(changer);

        DescriptionChanger updatedChanger = descrRepository.findById(EXISTED_DESCRIPTION_CHANGER_ID.id());
        assertThat(updatedChanger.getDescriptionPattern()).isEqualTo("new pattern");

    }

    @Test
    public void deleteDescriptionChanger() {
        descrRepository.deleteById(EXISTED_DESCRIPTION_CHANGER_ID.id());

        DescriptionChanger deletedChanger = descrRepository.findById(EXISTED_DESCRIPTION_CHANGER_ID.id());
        assertThat(deletedChanger).isNull();
    }
}
