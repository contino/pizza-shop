package io.contino.pizza.shop.repository.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service")
public record ServiceProperties(Topic topic) {
}
