package org.molodyko.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.User;
import org.molodyko.integration.IntegrationBase;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_CATEGORY_ID;

@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryRepositoryIT extends IntegrationBase {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Test
    void create() {
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        Category food = Category.builder().name("food").user(user).build();
        categoryRepository.saveAndFlush(food);

        Category createdCategory = categoryRepository.findById(CREATED_CATEGORY_ID.id()).orElseThrow();
        assertThat(createdCategory).isNotNull();
    }

    @Test
    void read() {
        Category category = categoryRepository.findById(EXISTED_CATEGORY_ID.id()).orElseThrow();

        assertThat(category.getName()).isEqualTo("vacation");
        assertThat(category.getUser().getUsername()).isEqualTo("abl");
    }

    @Test
    void update() {
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        Category category = Category.builder().id(EXISTED_CATEGORY_ID.id()).name("animal").user(user).build();
        categoryRepository.saveAndFlush(category);

        Category updatedCategory = categoryRepository.findById(EXISTED_CATEGORY_ID.id()).orElseThrow();
        assertThat(updatedCategory.getName()).isEqualTo("animal");
    }

    @Test
    void delete() {
        categoryRepository.deleteById(FOR_DELETE_CATEGORY_ID.id());

        Optional<Category> deletedCategory = categoryRepository.findById(FOR_DELETE_CATEGORY_ID.id());
        assertThat(deletedCategory).isEmpty();
    }
}
