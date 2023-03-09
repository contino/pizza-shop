package io.contino.transformer;

import io.contino.transformer.config.TracingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@AutoConfigureObservability
@ConfigurationPropertiesScan("io.contino.transformer")
@Import({TracingConfig.class})
public class
TransformerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransformerApplication.class, args);
	}

}
