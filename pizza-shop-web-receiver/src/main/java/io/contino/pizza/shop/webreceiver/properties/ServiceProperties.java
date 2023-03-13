package io.contino.pizza.shop.webreceiver.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service")
public record ServiceProperties(Api api, Topic topic) {
}
