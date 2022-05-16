package org.molodyko.integration;

import org.hibernate.Session;
import org.molodyko.entity.Category;
import org.molodyko.entity.CategoryRename;
import org.molodyko.entity.User;
import org.molodyko.repository.CategoryRenameRepository;
import org.molodyko.repository.CategoryRepository;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_CATEGORY_RENAME_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ANOTHER_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_CATEGORY_RENAME_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_CATEGORY_ID;

public class CategoryRenameRepositoryIT extends IntegrationBase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryRenameRepository categoryRenameRepository;

    public void create(Session session) {
        Category categoryBefore = categoryRepository.findById(EXISTED_CATEGORY_ID.id(), session);
        Category categoryAfter = categoryRepository.findById(EXISTED_CATEGORY_ANOTHER_ID.id(), session);
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        CategoryRename categoryRename = CategoryRename.builder()
                .categoryAfter(categoryAfter)
                .categoryBefore(categoryBefore)
                .user(user)
                .build();
        categoryRenameRepository.save(categoryRename, session);

        CategoryRename createdRenamer = categoryRenameRepository.findById(CREATED_CATEGORY_RENAME_ID.id(), session);
        assertThat(createdRenamer).isNotNull();

    }

    public void read(Session session) {
        CategoryRename categoryRename = categoryRenameRepository.findById(EXISTED_CATEGORY_RENAME_ID.id(), session);

        assertThat(categoryRename.getCategoryBefore().getName()).isEqualTo("vacation");
        assertThat(categoryRename.getCategoryAfter().getName()).isEqualTo("car");
        assertThat(categoryRename.getUser().getUsername()).isEqualTo("abl");
    }

    public void update(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);
        Category categoryBefore = categoryRepository.findById(EXISTED_CATEGORY_ANOTHER_ID.id(), session);
        Category categoryAfter = categoryRepository.findById(FOR_DELETE_CATEGORY_ID.id(), session);
        CategoryRename categoryRename = CategoryRename.builder()
                .id(EXISTED_CATEGORY_RENAME_ID.id())
                .user(user)
                .categoryBefore(categoryBefore)
                .categoryAfter(categoryAfter)
                .build();

        categoryRenameRepository.update(categoryRename, session);

        CategoryRename updatedRenamer = categoryRenameRepository.findById(EXISTED_CATEGORY_RENAME_ID.id(), session);
        assertThat(updatedRenamer.getCategoryAfter().getId()).isEqualTo(FOR_DELETE_CATEGORY_ID.id());
    }

    public void delete(Session session) {
        categoryRenameRepository.deleteById(EXISTED_CATEGORY_RENAME_ID.id(), session);

        CategoryRename deletedRenamer = categoryRenameRepository.findById(EXISTED_CATEGORY_RENAME_ID.id(), session);
        assertThat(deletedRenamer).isNull();
    }
}
