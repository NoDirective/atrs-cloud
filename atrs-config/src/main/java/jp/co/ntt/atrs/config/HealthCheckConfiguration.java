/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ntt.atrs.common.health.S3HealthIndicator;

@Configuration
public class HealthCheckConfiguration {

    @Bean
    public S3HealthIndicator s3HealthIndicator() {
        return new S3HealthIndicator();
    }

}
