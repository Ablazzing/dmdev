package org.molodyko.integration;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.User;
import org.molodyko.entity.UserRole;

import static org.assertj.core.api.Assertions.assertThat;

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

            User createdUser = session.get(User.class, CREATED_USER_ID);
            assertThat(createdUser).isNotNull();

            session.getTransaction().commit();
        }
    }

    @Test
    public void readUser() {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, EXISTED_USER_ID);

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
                    .id(EXISTED_USER_ID)
                    .username("abl")
                    .password("pass")
                    .email("test@ya.ru")
                    .role(UserRole.USER)
                    .build();
            session.update(user);
            session.flush();

            User updatedUser = session.get(User.class, EXISTED_USER_ID);
            assertThat(updatedUser.getRole()).isEqualTo(UserRole.USER);

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder().id(FOR_DELETE_USER_ID).build();
            session.delete(user);
            session.flush();

            User deletedUser = session.get(User.class, FOR_DELETE_USER_ID);
            assertThat(deletedUser).isNull();

            session.getTransaction().commit();
        }
    }
}
