package org.molodyko.integration;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.CategoryRename;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRenameRepository;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_CATEGORY_RENAME_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ANOTHER_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_RENAME_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_CATEGORY_ID;

@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryRenameRepositoryIT extends IntegrationBase {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryRenameRepository categoryRenameRepository;

    @Test
    void create() {
        Category categoryBefore = categoryRepository.findById(EXISTED_CATEGORY_ID.id()).orElseThrow();
        Category categoryAfter = categoryRepository.findById(EXISTED_CATEGORY_ANOTHER_ID.id()).orElseThrow();
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        CategoryRename categoryRename = CategoryRename.builder()
                .categoryAfter(categoryAfter)
                .categoryBefore(categoryBefore)
                .user(user)
                .build();
        categoryRenameRepository.saveAndFlush(categoryRename);

        CategoryRename createdRenamer = categoryRenameRepository.findById(CREATED_CATEGORY_RENAME_ID.id())
                .orElseThrow();
        assertThat(createdRenamer).isNotNull();

    }

    @Test
    void read() {
        CategoryRename categoryRename = categoryRenameRepository.findById(EXISTED_CATEGORY_RENAME_ID.id())
                .orElseThrow();

        assertThat(categoryRename.getCategoryBefore().getName()).isEqualTo("vacation");
        assertThat(categoryRename.getCategoryAfter().getName()).isEqualTo("car");
        assertThat(categoryRename.getUser().getUsername()).isEqualTo("abl");
    }

    @Test
    void update() {
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        Category categoryBefore = categoryRepository.findById(EXISTED_CATEGORY_ANOTHER_ID.id()).orElseThrow();
        Category categoryAfter = categoryRepository.findById(FOR_DELETE_CATEGORY_ID.id()).orElseThrow();
        CategoryRename categoryRename = CategoryRename.builder()
                .id(EXISTED_CATEGORY_RENAME_ID.id())
                .user(user)
                .categoryBefore(categoryBefore)
                .categoryAfter(categoryAfter)
                .build();

        categoryRenameRepository.saveAndFlush(categoryRename);

        CategoryRename updatedRenamer = categoryRenameRepository.findById(EXISTED_CATEGORY_RENAME_ID.id())
                .orElseThrow();
        assertThat(updatedRenamer.getCategoryAfter().getId()).isEqualTo(FOR_DELETE_CATEGORY_ID.id());
    }

    @Test
    void delete() {
        categoryRenameRepository.deleteById(EXISTED_CATEGORY_RENAME_ID.id());

        Optional<CategoryRename> deletedRenamer = categoryRenameRepository.findById(EXISTED_CATEGORY_RENAME_ID.id());
        assertThat(deletedRenamer).isEmpty();
    }
}
