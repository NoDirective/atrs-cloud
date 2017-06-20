/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.config;

import javax.inject.Inject;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.server.config.ConfigServerProperties;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
@EnableConfigurationProperties(ConfigServerProperties.class)
public class CustomEnvironmentRepositoryConfiguration {

    @Configuration
    @Profile("s3")
    protected static class S3RepositoryConfiguration {

        @Inject
        private ConfigurableEnvironment environment;

        @Bean
        public EnvironmentRepository environmentRepository() {
            return new S3EnvironmentRepository(this.environment);
        }

    }
}
