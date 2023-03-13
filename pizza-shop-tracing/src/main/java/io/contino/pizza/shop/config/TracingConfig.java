package io.contino.pizza.shop.config;

import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class TracingConfig {

    @Bean(name = "orderId")
    public BaggageField orderId() {
        return BaggageField.create("orderId");
    }

    @Bean(name = "customerId")
    public BaggageField customerId() {
        return BaggageField.create("customerId");
    }

    @Bean
    public CurrentTraceContext.ScopeDecorator mdcScopeDecorator(@Qualifier("orderId") BaggageField orderId, @Qualifier("customerId") BaggageField customerId) {
        return MDCScopeDecorator.newBuilder().clear()
                .add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(orderId).flushOnUpdate().build())
                .add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(customerId).flushOnUpdate().build())
                .build();
    }

}
