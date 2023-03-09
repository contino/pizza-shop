package io.contino.transformer.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service")
public record ServiceProperties(Inbound inbound, Outbound outbound) {
}
