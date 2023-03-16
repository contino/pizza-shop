package io.contino.pizza.shop.repository.services;

import io.contino.pizza.shop.models.Order;
import io.contino.pizza.shop.repository.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderListener {

    private final OrderRepository orderRepository;

    @KafkaListener(id = "${service.topic.listener-id}", topics = "${service.topic.topic-name}")
    public void insertOrder(Order order) {
        orderRepository.insertOrder(order);
        log.info("order stored");
    }
}

