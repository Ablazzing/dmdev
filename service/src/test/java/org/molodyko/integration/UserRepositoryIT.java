package org.molodyko.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.User;
import org.molodyko.entity.UserRole;
import org.molodyko.entity.filter.UserFilter;
import org.molodyko.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_USER_ID;

public class UserRepositoryIT extends IntegrationBase {
    private final UserRepository userRepository = new UserRepository(sessionFactory);

    @Test
    public void createUser() {
        User user = User.builder()
                .username("ablazzing")
                .email("y288@ay.ru")
                .role(UserRole.ADMIN)
                .password("kkkk")
                .build();

        userRepository.save(user);
    }

    @Test
    public void readUser() {
        User user = userRepository.findById(EXISTED_USER_ID.id());

        assertThat(user.getEmail()).isEqualTo("test@ya.ru");
        assertThat(user.getPassword()).isEqualTo("pass");
        assertThat(user.getUsername()).isEqualTo("abl");
        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    public void updateUser() {
        User user = User.builder()
                .id(EXISTED_USER_ID.id())
                .username("abl")
                .password("pass")
                .email("test@ya.ru")
                .role(UserRole.USER)
                .build();
        userRepository.update(user);

        User updatedUser = userRepository.findById(EXISTED_USER_ID.id());
        assertThat(updatedUser.getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    public void deleteUser() {
        userRepository.deleteById(FOR_DELETE_USER_ID.id());
        User deletedUser = userRepository.findById(FOR_DELETE_USER_ID.id());
        assertThat(deletedUser).isNull();
    }

    @Test
    public void checkUserFilter() {
        UserFilter filter = UserFilter.builder().username(null).role(UserRole.ADMIN).build();

        List<User> list = userRepository.getUsersByFilter(filter);

        Assertions.assertThat(list).hasSize(2);
    }
}
