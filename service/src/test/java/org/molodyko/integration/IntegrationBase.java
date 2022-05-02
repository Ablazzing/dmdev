package org.molodyko.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.molodyko.HibernateConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public abstract class IntegrationBase {
    protected static final Configuration configuration = HibernateConfig.getConfig();
    protected static final SessionFactory sessionFactory = configuration.buildSessionFactory();

    private final String sqlCreateTables = readSqlScript("create_tables.sql");

    private final String sqlInsertData = readSqlScript("insert_data.sql");

    private String sqlDropTables = "DROP ALL OBJECTS";

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

    @AfterEach
    protected void clearDatabase() {
        try (
                Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery(sqlDropTables).executeUpdate();

            session.getTransaction().commit();
        }
    }

    private static String readSqlScript(String filename) {
        InputStream dataStream = IntegrationBase.class.getClassLoader().getResourceAsStream(filename);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataStream, StandardCharsets.UTF_8));
        return bufferedReader.lines().collect(Collectors.joining());
    }

}
