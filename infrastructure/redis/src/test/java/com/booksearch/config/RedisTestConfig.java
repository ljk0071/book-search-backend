package com.booksearch.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class RedisTestConfig {

    private static final String REDIS_DOCKET_IMAGE = "redis:alpine";

    static {
        GenericContainer<?> REDIS = new GenericContainer<>(DockerImageName.parse(REDIS_DOCKET_IMAGE))
                .withExposedPorts(6379)
                .withReuse(true);

        REDIS.start();

        System.setProperty("spring.redis.host", REDIS.getHost());
        System.setProperty("spring.redis.port", REDIS.getMappedPort(6379).toString());
    }
}
