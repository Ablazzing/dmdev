package org.molodyko.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.molodyko.entity.Category;
import org.molodyko.entity.CategoryRename;
import org.molodyko.entity.DescriptionChanger;
import org.molodyko.entity.Entry;
import org.molodyko.entity.Holiday;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;

public abstract class IntegrationBase {
    private final Configuration configuration;

    private final String sql = """
            insert into users (username, password, email, role) values('abl', 'pass', 'test@ya.ru', 'ADMIN');
            insert into category (name, user_id) values('vacation', 1);
            insert into category (name, user_id) values('car', 1);
            insert into category (name, user_id) values('food', 1);
            insert into holiday_type (name, category_id, user_id) values('отпуск на море', 1, 1);
            insert into holiday (start_date, end_date, user_id, holiday_type_id) values('2020-01-01','2021-01-01', 1, 1);
            insert into category_rename (category_before_id, category_after_id, user_id) values(1, 2, 1);
            insert into description_changer (category_id, user_id, description_pattern) values(1, 1, 'some_text');          
            insert into entry (amount, description, date, operation_number, category_id, user_id) values (1000, 'some_text', '2020-01-01 00:00:00', 1, 1, 1);
            """;

    SessionFactory sessionFactory;

    public IntegrationBase() {
        configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(HolidayType.class);
        configuration.addAnnotatedClass(Holiday.class);
        configuration.addAnnotatedClass(CategoryRename.class);
        configuration.addAnnotatedClass(DescriptionChanger.class);
        configuration.addAnnotatedClass(Entry.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();
        this.sessionFactory = configuration.buildSessionFactory();
    }

    @BeforeEach
    protected void fillDatabaseTestData() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery(sql).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @AfterEach
    protected void close() {
        sessionFactory.close();
    }
}
