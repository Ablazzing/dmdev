package org.molodyko.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.molodyko.HibernateConfig;

public abstract class IntegrationBase {
    protected static final Configuration configuration = HibernateConfig.getConfig();
    protected static final SessionFactory sessionFactory = configuration.buildSessionFactory();
    protected static final int CREATED_USER_ID = 3;
    protected static final int EXISTED_USER_ID = 1;
    protected static final int FOR_DELETE_USER_ID = 2;
    protected static final int EXISTED_CATEGORY_ID = 1;
    protected static final int EXISTED_CATEGORY_ANOTHER_ID = 2;
    protected static final int FOR_DELETE_CATEGORY_ID = 3;
    protected static final int EXISTED_HOLIDAY_TYPE_ID = 1;
    protected static final int FOR_DELETE_HOLIDAY_TYPE_ID = 2;
    protected static final int EXISTED_HOLIDAY_ID = 1;
    protected static final int EXISTED_ENTRY_ID = 1;
    protected static final int EXISTED_DESCRIPTION_CHANGER_ID = 1;
    protected static final int EXISTED_CATEGORY_RENAME_ID = 1;



    private final String sqlCreateTables = """           
                   CREATE TABLE IF NOT EXISTS public.users
                   (
                       id       SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE,
                       password VARCHAR(50) NOT NULL,
                       email    VARCHAR(50) UNIQUE,
                       role     VARCHAR(50) NOT NULL
                   );
                   
                   CREATE TABLE IF NOT EXISTS public.category
                   (
                       id      SERIAL PRIMARY KEY,
                       name    VARCHAR(50) NOT NULL,
                       user_id INTEGER NOT NULL,
                       foreign key (user_id) references public.users (id)
                   );
                   
                   
                   CREATE TABLE IF NOT EXISTS public.holiday_type
                   (
                       id          SERIAL PRIMARY KEY,
                       name        VARCHAR(50) UNIQUE,
                       category_id INTEGER NOT NULL,
                       user_id     INTEGER NOT NULL,
                       foreign key (category_id) references public.category (id)
                   );
                   
                   CREATE TABLE IF NOT EXISTS public.holiday
                   (
                       id              SERIAL PRIMARY KEY,
                       start_date      DATE NOT NULL,
                       end_date        DATE NOT NULL,
                       user_id         INTEGER NOT NULL,
                       holiday_type_id INTEGER NOT NULL,
                       foreign key (user_id) references public.users (id),
                       foreign key (holiday_type_id) references public.holiday_type (id)
                   );
                   
                   
                   CREATE TABLE IF NOT EXISTS public.entry
                   (
                       id               SERIAL PRIMARY KEY,
                       amount           DECIMAL NOT NULL,
                       description      VARCHAR(100) NOT NULL,
                       date             TIMESTAMP NOT NULL,
                       operation_number INTEGER NOT NULL,
                       category_id      INTEGER NOT NULL,
                       user_id          INTEGER NOT NULL,
                       foreign key (user_id) references public.users (id),
                       foreign key (category_id) references public.category (id)
                   );
                   
                   CREATE TABLE IF NOT EXISTS public.category_rename
                   (
                       id                 SERIAL PRIMARY KEY,
                       category_before_id INTEGER NOT NULL,
                       category_after_id  INTEGER NOT NULL,
                       user_id            INTEGER NOT NULL,
                       foreign key (user_id) references public.users (id),
                       foreign key (category_before_id) references public.category (id),
                       foreign key (category_after_id) references public.category (id)
                   );
                   
                   CREATE TABLE IF NOT EXISTS public.description_changer
                   (
                       id                  SERIAL PRIMARY KEY,
                       description_pattern VARCHAR(100) NOT NULL,
                       category_id         INTEGER NOT NULL,
                       user_id             INTEGER NOT NULL,
                       foreign key (user_id) references public.users (id),
                       foreign key (category_id) references public.category (id)
                   );
            """;

    private final String sqlInsertData = """
            insert into users (username, password, email, role) values('abl', 'pass', 'test@ya.ru', 'ADMIN');
            insert into users (username, password, email, role) values('petr', 'pass', 'test2@ya.ru', 'ADMIN');
            insert into category (name, user_id) values('vacation', 1);
            insert into category (name, user_id) values('car', 1);
            insert into category (name, user_id) values('food', 1);
            insert into holiday_type (name, category_id, user_id) values('отпуск на море', 1, 1);
            insert into holiday_type (name, category_id, user_id) values('отпуск в деревне', 1, 1);
            insert into holiday (start_date, end_date, user_id, holiday_type_id) values('2020-01-01','2021-01-01', 1, 1);
            insert into category_rename (category_before_id, category_after_id, user_id) values(1, 2, 1);
            insert into description_changer (category_id, user_id, description_pattern) values(1, 1, 'some_text');          
            insert into entry (amount, description, date, operation_number, category_id, user_id) values (1000, 'some_text', '2020-01-01 00:00:00', 1, 1, 1);
            insert into entry (amount, description, date, operation_number, category_id, user_id) values (2000, 'some_text', '2021-01-01 00:00:00', 2, 1, 1);
            insert into entry (amount, description, date, operation_number, category_id, user_id) values (2000, 'some_text', '2020-12-01 00:00:00', 3, 1, 1);
            """;

    private String sqlDropTables = """
            DROP ALL OBJECTS
            """;


    @BeforeEach
    protected void fillDatabaseTestData() {
        try (
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery(sqlCreateTables).executeUpdate();
            session.createSQLQuery(sqlInsertData).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @AfterEach protected void clearDatabase() {
        try (
                Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery(sqlDropTables).executeUpdate();

            session.getTransaction().commit();
        }
    }

}
