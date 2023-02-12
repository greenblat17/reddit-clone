package com.greenblat.redditclone;

import org.testcontainers.containers.PostgreSQLContainer;

public class BaseTest {

    static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest");

    static {
        postgreSQLContainer.start();
    }
}
