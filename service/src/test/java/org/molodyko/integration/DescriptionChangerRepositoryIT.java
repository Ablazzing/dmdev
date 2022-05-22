package org.molodyko.integration;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.DescriptionChanger;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.DescriptionChangerRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_DESCRIPTION_CHANGER_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_DESCRIPTION_CHANGER_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DescriptionChangerRepositoryIT extends IntegrationBase {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DescriptionChangerRepository descrRepository;

    @Test
    public void create() {
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
    public void read() {
        DescriptionChanger descriptionChanger = descrRepository.findById(EXISTED_DESCRIPTION_CHANGER_ID.id());

        assertThat(descriptionChanger.getDescriptionPattern()).isEqualTo("some_text");
        assertThat(descriptionChanger.getUser().getUsername()).isEqualTo("abl");
        assertThat(descriptionChanger.getCategory().getName()).isEqualTo("vacation");
    }

    @Test
    public void update() {
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
    public void delete() {
        descrRepository.deleteById(EXISTED_DESCRIPTION_CHANGER_ID.id());

        DescriptionChanger deletedChanger = descrRepository.findById(EXISTED_DESCRIPTION_CHANGER_ID.id());
        assertThat(deletedChanger).isNull();
    }
}
