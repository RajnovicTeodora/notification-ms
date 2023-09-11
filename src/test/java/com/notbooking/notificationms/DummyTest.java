package com.notbooking.notificationms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = NotificationMsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:tc:postgresql:11-alpine:///notBookingDBTest" })
@ActiveProfiles("test")
public class DummyTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DummyTest.class);

    @Container
    private static final PostgreSQLContainer POSTGRES_SQL_CONTAINER;

    static {

        POSTGRES_SQL_CONTAINER = (PostgreSQLContainer) new PostgreSQLContainer("postgres:11.1")
                .withUsername("postgres")
                .withPassword("postgres")
                .withDatabaseName("notBookingDBTest")
                .withPrivilegedMode(true);

        POSTGRES_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {

        System.out.println(POSTGRES_SQL_CONTAINER.getJdbcUrl());
        LOGGER.info("JDBC URL: {}", POSTGRES_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.url", () -> "jdbc:tc:postgresql:11-alpine:///notBookingDBTest?loggerLevel=OFF"); // POSTGRES_SQL_CONTAINER::getJdbcUrl);
        registry.add("javax.persistence.jdbc.url",
                () -> "jdbc:tc:postgresql:11-alpine:///notBookingDBTest?loggerLevel=OFF");
        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driverClassName", () -> "org.testcontainers.jdbc.ContainerDatabaseDriver");

        registry.add("integration-tests-db", POSTGRES_SQL_CONTAINER::getDatabaseName);

    }

    @Test
    void testAssertTrue() {

        Assertions.assertTrue(true);
    }
}
