package io.contino.transformer.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.contino.transformer.models.Address;
import io.contino.transformer.models.Order;
import io.contino.transformer.repositories.CustomerAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.ValueTransformerWithKey;
import org.apache.kafka.streams.kstream.ValueTransformerWithKeySupplier;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderTransformer implements ValueTransformerWithKeySupplier<String, String, Order>, ValueTransformerWithKey<String, String, Order> {

    private final CustomerAddressRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public Order transform(String key, String orderString) {
        Order order = objectMapper.readValue(orderString, Order.class);
        Address address = repository.findByCustomerId(order.customerId());
        Order result = order.withId(key).withAddress(address);
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
    public ValueTransformerWithKey get() {
        return new OrderTransformer(repository, objectMapper);
    }
}
