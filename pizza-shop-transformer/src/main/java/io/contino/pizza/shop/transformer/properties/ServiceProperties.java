package io.contino.pizza.shop.transformer.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service")
public record ServiceProperties(Inbound inbound, Outbound outbound) {
}
