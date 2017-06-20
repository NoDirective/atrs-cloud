/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.common.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.aws.autoconfigure.cache.ElastiCacheAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@ImportResource({ "classpath:/META-INF/spring/spring-security.xml",
        "classpath:/META-INF/spring/applicationContext.xml",
        "classpath:/META-INF/spring/spring-mvc.xml" })
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
        ElastiCacheAutoConfiguration.class, JmxAutoConfiguration.class,
        WebMvcAutoConfiguration.class })
public class Bootstrap extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        setRegisterErrorPageFilter(false);
        return application.sources(Bootstrap.class);
    }

}
