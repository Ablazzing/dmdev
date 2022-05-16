package org.molodyko.integration;

import org.hibernate.Session;
import org.molodyko.entity.Category;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_CATEGORY_ID;

public class CategoryRepositoryIT extends IntegrationBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void create(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        Category food = Category.builder().name("food").user(user).build();
        categoryRepository.save(food, session);

        Category createdCategory = categoryRepository.findById(CREATED_CATEGORY_ID.id(), session);
        assertThat(createdCategory).isNotNull();
    }

    public void read(Session session) {
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id(), session);

        assertThat(category.getName()).isEqualTo("vacation");
        assertThat(category.getUser().getUsername()).isEqualTo("abl");
    }

    public void update(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        Category category = Category.builder().id(EXISTED_CATEGORY_ID.id()).name("animal").user(user).build();
        categoryRepository.update(category, session);

        Category updatedCategory = categoryRepository.findById(EXISTED_CATEGORY_ID.id(), session);
        assertThat(updatedCategory.getName()).isEqualTo("animal");
    }

    public void delete(Session session) {
        categoryRepository.deleteById(FOR_DELETE_CATEGORY_ID.id(), session);

        Category deletedCategory = categoryRepository.findById(FOR_DELETE_CATEGORY_ID.id(), session);
        assertThat(deletedCategory).isNull();
    }
}
