package org.molodyko.integration;

import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_CATEGORY_ID;

public class CategoryRepositoryIT extends IntegrationBase {
    private final UserRepository userRepository = new UserRepository(sessionFactory);
    private final CategoryRepository categoryRepository = new CategoryRepository(sessionFactory);

    @Test
    public void createCategory() {
        User user = userRepository.findById(EXISTED_USER_ID.id());
        Category food = Category.builder().name("food").user(user).build();
        categoryRepository.save(food);

        Category createdCategory = categoryRepository.findById(CREATED_CATEGORY_ID.id());
        assertThat(createdCategory).isNotNull();
    }

    @Test
    public void readCategory() {
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id());

        assertThat(category.getName()).isEqualTo("vacation");
        assertThat(category.getUser().getUsername()).isEqualTo("abl");
    }

    @Test
    public void updateCategory() {
        User user = userRepository.findById(EXISTED_USER_ID.id());
        Category category = Category.builder().id(EXISTED_CATEGORY_ID.id()).name("animal").user(user).build();
        categoryRepository.update(category);

        Category updatedCategory = categoryRepository.findById(EXISTED_CATEGORY_ID.id());
        assertThat(updatedCategory.getName()).isEqualTo("animal");
    }

    @Test
    public void deleteCategory() {
        categoryRepository.deleteById(FOR_DELETE_CATEGORY_ID.id());

        Category deletedCategory = categoryRepository.findById(FOR_DELETE_CATEGORY_ID.id());
        assertThat(deletedCategory).isNull();
    }
}
