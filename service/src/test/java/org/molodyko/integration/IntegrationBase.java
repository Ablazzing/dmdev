package org.molodyko.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@SpringBootTest
public abstract class IntegrationBase {
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1");

    private final String sqlInsertData = readSqlScript("insert_data.sql");
    private final String sqlCleanData = readSqlScript("clean_data.sql");

    @Autowired
    private EntityManager entityManager;

    @BeforeAll
    private static void startContainer() {
        container.start();
    }

    @DynamicPropertySource
    private static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    private static String readSqlScript(String filename) {
        InputStream dataStream = IntegrationBase.class.getClassLoader().getResourceAsStream(filename);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataStream, StandardCharsets.UTF_8));
        return bufferedReader.lines().collect(Collectors.joining());
    }

    @BeforeEach
    protected void clearDatabase() {
        entityManager.createNativeQuery(sqlCleanData).executeUpdate();
        entityManager.createNativeQuery(sqlInsertData).executeUpdate();
    }
}
