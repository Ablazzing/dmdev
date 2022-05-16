package org.molodyko.integration;

import org.hibernate.Session;
import org.molodyko.entity.Category;
import org.molodyko.entity.DescriptionChanger;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.DescriptionChangerRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_DESCRIPTION_CHANGER_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_DESCRIPTION_CHANGER_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;

public class DescriptionChangerRepositoryIT extends IntegrationBase {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private DescriptionChangerRepository descrRepository;

    @Override
    public void create(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id(), session);

        DescriptionChanger descriptionChanger = DescriptionChanger.builder()
                .user(user)
                .category(category)
                .descriptionPattern("some_text2")
                .build();
        descrRepository.save(descriptionChanger, session);

        DescriptionChanger changer = descrRepository.findById(CREATED_DESCRIPTION_CHANGER_ID.id(), session);
        assertThat(changer).isNotNull();
    }

    @Override
    public void read(Session session) {
        DescriptionChanger descriptionChanger = descrRepository.findById(EXISTED_DESCRIPTION_CHANGER_ID.id(), session);

        assertThat(descriptionChanger.getDescriptionPattern()).isEqualTo("some_text");
        assertThat(descriptionChanger.getUser().getUsername()).isEqualTo("abl");
        assertThat(descriptionChanger.getCategory().getName()).isEqualTo("vacation");
    }

    @Override
    public void update(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id(), session);
        DescriptionChanger changer = DescriptionChanger.builder()
                .user(user)
                .category(category)
                .descriptionPattern("new pattern")
                .id(EXISTED_DESCRIPTION_CHANGER_ID.id()).build();
        descrRepository.update(changer, session);

        DescriptionChanger updatedChanger = descrRepository.findById(EXISTED_DESCRIPTION_CHANGER_ID.id(), session);
        assertThat(updatedChanger.getDescriptionPattern()).isEqualTo("new pattern");

    }

    @Override
    public void delete(Session session) {
        descrRepository.deleteById(EXISTED_DESCRIPTION_CHANGER_ID.id(), session);

        DescriptionChanger deletedChanger = descrRepository.findById(EXISTED_DESCRIPTION_CHANGER_ID.id(), session);
        assertThat(deletedChanger).isNull();
    }
}
