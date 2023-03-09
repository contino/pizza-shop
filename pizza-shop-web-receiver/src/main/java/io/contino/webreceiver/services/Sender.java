package io.contino.webreceiver.services;

import io.contino.transformer.models.Order;
import io.contino.webreceiver.properties.ServiceProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Sender {

    @NonNull
    private final KafkaTemplate<String, Order> kafkaTemplate;
    @NonNull
    private final ServiceProperties serviceProperties;

    public void send(@NonNull Order order) {
        kafkaTemplate.send(serviceProperties.topic().topicName(), order.id(), order);
    }
}
