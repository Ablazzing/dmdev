package org.molodyko.integration;

import org.assertj.core.api.Assertions;
import org.hibernate.Session;
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

    public void create(Session session) {
        User user = User.builder()
                .username("ablazzing")
                .email("y288@ay.ru")
                .role(UserRole.ADMIN)
                .password("kkkk")
                .build();

        userRepository.save(user, session);
    }

    public void read(Session session) {
        User user = userRepository.findById(EXISTED_USER_ID.id(), session);

        assertThat(user.getEmail()).isEqualTo("test@ya.ru");
        assertThat(user.getPassword()).isEqualTo("pass");
        assertThat(user.getUsername()).isEqualTo("abl");
        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
    }

    public void update(Session session) {
        User user = User.builder()
                .id(EXISTED_USER_ID.id())
                .username("abl")
                .password("pass")
                .email("test@ya.ru")
                .role(UserRole.USER)
                .build();
        userRepository.update(user, session);

        User updatedUser = userRepository.findById(EXISTED_USER_ID.id(), session);
        assertThat(updatedUser.getRole()).isEqualTo(UserRole.USER);
    }

    public void delete(Session session) {
        userRepository.deleteById(FOR_DELETE_USER_ID.id(), session);
        User deletedUser = userRepository.findById(FOR_DELETE_USER_ID.id(), session);
        assertThat(deletedUser).isNull();
    }

    @Test
    public void checkUserFilter() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            UserFilter filter = UserFilter.builder().username(null).role(UserRole.ADMIN).build();

            List<User> list = userRepository.getUsersByFilter(filter, session);

            Assertions.assertThat(list).hasSize(2);
            session.getTransaction().commit();
        }
    }
}
