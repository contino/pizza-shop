package io.contino.pizza.repository;

import io.contino.transformer.config.TracingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.tracing.BraveAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@AutoConfigureObservability
@ConfigurationPropertiesScan("io.contino.store")
@Import({TracingConfig.class, BraveAutoConfiguration.class})
public class RepositoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(RepositoryApplication.class, args);
	}
}
