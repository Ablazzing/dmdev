package org.molodyko.integration;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.User;
import org.molodyko.entity.UserRole;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class UsersEntityIT extends IntegrationBase {

    @Test
    public void createUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("ablazzing")
                    .email("y288@ay.ru")
                    .role(UserRole.ADMIN)
                    .password("kkkk")
                    .build();
            session.save(user);

            session.getTransaction().commit();
        }
    }

    @Test
    public void readUser() {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, 1);

            assertThat(user.getEmail()).isEqualTo("test@ya.ru");
            assertThat(user.getPassword()).isEqualTo("pass");
            assertThat(user.getUsername()).isEqualTo("abl");
            assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
        }
    }

    @Test
    public void updateUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .id(1)
                    .username("abl")
                    .password("pass")
                    .email("test@ya.ru")
                    .role(UserRole.USER)
                    .build();
            session.update(user);
            session.flush();

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder().id(1).build();
            session.delete(user);
            session.flush();

            session.getTransaction().commit();
        }
    }
}
