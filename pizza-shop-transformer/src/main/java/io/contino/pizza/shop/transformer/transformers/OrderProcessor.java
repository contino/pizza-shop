package io.contino.pizza.shop.transformer.transformers;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.contino.pizza.shop.models.Address;
import io.contino.pizza.shop.models.Order;
import io.contino.pizza.shop.transformer.repositories.CustomerAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.processor.api.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderProcessor implements FixedKeyProcessorSupplier<String, Order, Order>, FixedKeyProcessor<String, Order, Order> {

    private final CustomerAddressRepository repository;
    private final ObjectMapper objectMapper;
    private final Tracer tracer;
    private FixedKeyProcessorContext<String, Order> context;

    public void init(FixedKeyProcessorContext<String, Order> context) {
        this.context = context;
    }

    @Override
    public void process(FixedKeyRecord<String, Order> record) {
        Address address = repository.findByCustomerId(record.value().customerId());
        context.forward(record.withValue(record.value().withId(record.key()).withAddress(address)));
        log.info("successfully transformed order");
    }

    @Override
    public FixedKeyProcessor<String, Order, Order> get() {
        return new OrderProcessor(repository, objectMapper, tracer);
    }

    public Set<StoreBuilder<?>> stores() {
        StoreBuilder<KeyValueStore<String, String>> keyValueStoreBuilder =
                Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore("process"),
                        Serdes.String(),
                        new JsonSerde<>());
        return Collections.singleton(keyValueStoreBuilder);
    }
}
