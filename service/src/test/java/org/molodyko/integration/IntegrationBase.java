package org.molodyko.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.molodyko.Runner;
import org.molodyko.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@SpringBootTest(classes = {Runner.class, SpringConfig.class})
public abstract class IntegrationBase {
    @Autowired
    protected SessionFactory sessionFactory;

    private final String sqlCreateTables = readSqlScript("create_tables.sql");

    private final String sqlInsertData = readSqlScript("insert_data.sql");

    private String sqlDropTables = "DROP ALL OBJECTS";

    @BeforeEach
    protected void fillDatabaseTestData() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery(sqlCreateTables).executeUpdate();
            session.createSQLQuery(sqlInsertData).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @AfterEach
    protected void clearDatabase() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery(sqlDropTables).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Test
    void createWithTransactional() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            create(session);
            session.getTransaction().commit();
        }
    }

    @Test
    void readWithTransactional() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            read(session);
            session.getTransaction().commit();
        }
    }

    @Test
    void updateWithTransactional() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            update(session);
            session.getTransaction().commit();
        }
    }

    @Test
    void deleteWithTransactional() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            delete(session);
            session.getTransaction().commit();
        }
    }




    abstract void create(Session session);
    abstract void update(Session session);
    abstract void delete(Session session);
    abstract void read(Session session);

    private static String readSqlScript(String filename) {
        InputStream dataStream = IntegrationBase.class.getClassLoader().getResourceAsStream(filename);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataStream, StandardCharsets.UTF_8));
        return bufferedReader.lines().collect(Collectors.joining());
    }

}
