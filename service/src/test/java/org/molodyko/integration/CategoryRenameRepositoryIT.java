package org.molodyko.integration;

import org.junit.jupiter.api.Test;
import org.molodyko.entity.Category;
import org.molodyko.entity.CategoryRename;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRenameRepository;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_CATEGORY_RENAME_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ANOTHER_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_RENAME_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_CATEGORY_ID;

public class CategoryRenameRepositoryIT extends IntegrationBase {
    private final UserRepository userRepository = new UserRepository(sessionFactory);
    private final CategoryRepository categoryRepository = new CategoryRepository(sessionFactory);
    private final CategoryRenameRepository categoryRenameRepository = new CategoryRenameRepository(sessionFactory);

    @Test
    public void createCategoryRename() {

        Category categoryBefore = categoryRepository.findById(EXISTED_CATEGORY_ID.id());
        Category categoryAfter = categoryRepository.findById(EXISTED_CATEGORY_ANOTHER_ID.id());
        User user = userRepository.findById(EXISTED_USER_ID.id());
        CategoryRename categoryRename = CategoryRename.builder()
                .categoryAfter(categoryAfter)
                .categoryBefore(categoryBefore)
                .user(user)
                .build();
        categoryRenameRepository.save(categoryRename);

        CategoryRename createdRenamer = categoryRenameRepository.findById(CREATED_CATEGORY_RENAME_ID.id());
        assertThat(createdRenamer).isNotNull();

    }

    @Test
    public void readCategoryRename() {
        CategoryRename categoryRename = categoryRenameRepository.findById(EXISTED_CATEGORY_RENAME_ID.id());

        assertThat(categoryRename.getCategoryBefore().getName()).isEqualTo("vacation");
        assertThat(categoryRename.getCategoryAfter().getName()).isEqualTo("car");
        assertThat(categoryRename.getUser().getUsername()).isEqualTo("abl");
    }

    @Test
    public void updateCategoryRename() {

        User user = userRepository.findById(EXISTED_USER_ID.id());
        Category categoryBefore = categoryRepository.findById(EXISTED_CATEGORY_ANOTHER_ID.id());
        Category categoryAfter = categoryRepository.findById(FOR_DELETE_CATEGORY_ID.id());
        CategoryRename categoryRename = CategoryRename.builder()
                .id(EXISTED_CATEGORY_RENAME_ID.id())
                .user(user)
                .categoryBefore(categoryBefore)
                .categoryAfter(categoryAfter)
                .build();

        categoryRenameRepository.update(categoryRename);

        CategoryRename updatedRenamer = categoryRenameRepository.findById(EXISTED_CATEGORY_RENAME_ID.id());
        assertThat(updatedRenamer.getCategoryAfter().getId()).isEqualTo(FOR_DELETE_CATEGORY_ID.id());
    }

    @Test
    public void deleteCategoryRename() {
        categoryRenameRepository.deleteById(EXISTED_CATEGORY_RENAME_ID.id());

        CategoryRename deletedRenamer = categoryRenameRepository.findById(EXISTED_CATEGORY_RENAME_ID.id());
        assertThat(deletedRenamer).isNull();
    }
}
