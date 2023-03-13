package io.contino.pizza.shop.transformer.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.contino.pizza.shop.models.Address;
import io.contino.pizza.shop.models.Order;
import io.contino.pizza.shop.transformer.repositories.CustomerAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderTransformer implements ValueTransformerSupplier<Order, Order>, ValueTransformer<Order, Order> {

    private final CustomerAddressRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public Order transform(Order order) {
        Address address = repository.findByCustomerId(order.customerId());
        Order result = order.withAddress(address);
        log.info("successfully transformed order");
        return result;
    }

    @Override
    public void init(ProcessorContext processorContext) {
        log.trace("initialising transformer");
    }

    @Override
    public void close() {
        log.trace("closing transformer");
    }

    @Override
    public ValueTransformer<Order, Order> get() {
        return new OrderTransformer(repository, objectMapper);
    }
}
