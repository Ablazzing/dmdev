package org.molodyko.integration;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.User;
import org.molodyko.entity.UserRole;
import org.molodyko.entity.filter.UserFilter;
import org.molodyko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.molodyko.integration.DababaseEntityId.CREATED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.EXISTED_USER_ID;
import static org.molodyko.integration.DababaseEntityId.FOR_DELETE_USER_ID;

@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryIT extends IntegrationBase {

    private final UserRepository userRepository;

    @Test
    public void create() {
        User user = User.builder()
                .username("ablazzing")
                .email("y288@ay.ru")
                .role(UserRole.ADMIN)
                .password("kkkk")
                .build();

        userRepository.saveAndFlush(user);
        User newUser = userRepository.findById(CREATED_USER_ID.id()).orElseThrow();
        assertThat(newUser).isNotNull();
    }

    @Test
    public void read() {
        User user = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();

        assertThat(user.getEmail()).isEqualTo("test@ya.ru");
        assertThat(user.getPassword()).isEqualTo("pass");
        assertThat(user.getUsername()).isEqualTo("abl");
        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    public void update() {
        User user = User.builder()
                .id(EXISTED_USER_ID.id())
                .username("abl")
                .password("pass")
                .email("test@ya.ru")
                .role(UserRole.USER)
                .build();
        userRepository.saveAndFlush(user);

        User updatedUser = userRepository.findById(EXISTED_USER_ID.id()).orElseThrow();
        assertThat(updatedUser.getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    public void delete() {
        userRepository.deleteById(FOR_DELETE_USER_ID.id());
        Optional<User> deletedUser = userRepository.findById(FOR_DELETE_USER_ID.id());
        assertThat(deletedUser).isEmpty();
    }

    @Test
    public void checkUserFilter() {
        UserFilter filter = UserFilter.builder().username(null).role(UserRole.ADMIN).build();

        List<User> list = userRepository.getUsersByFilter(filter);

        assertThat(list).hasSize(2);
    }
}
