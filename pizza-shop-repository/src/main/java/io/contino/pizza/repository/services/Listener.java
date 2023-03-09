package io.contino.pizza.repository.services;

import io.contino.transformer.models.Order;
import io.contino.pizza.repository.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Listener {

    private final OrderRepository orderRepository;

    @KafkaListener(id = "${service.topic.listener-id}", topics = "${service.topic.topic-name}")
    public void listen(Order order) {
        orderRepository.insertOrder(order);
        log.info("order stored");
    }

}

