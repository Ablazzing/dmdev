package org.molodyko.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@SpringBootTest
public abstract class IntegrationBase {
    private final String sqlCreateTables = readSqlScript("create_tables.sql");
    private final String sqlInsertData = readSqlScript("insert_data.sql");

    @Autowired
    EntityManager entityManager;

    private final String sqlDropTables = "DROP ALL OBJECTS";

    private static String readSqlScript(String filename) {
        InputStream dataStream = IntegrationBase.class.getClassLoader().getResourceAsStream(filename);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataStream, StandardCharsets.UTF_8));
        return bufferedReader.lines().collect(Collectors.joining());
    }

    @BeforeEach
    protected void fillDatabaseTestData() {
        entityManager.createNativeQuery(sqlCreateTables).executeUpdate();
        entityManager.createNativeQuery(sqlInsertData).executeUpdate();
    }

    @AfterEach
    protected void clearDatabase() {
        entityManager.createNativeQuery(sqlDropTables).executeUpdate();
    }
}
