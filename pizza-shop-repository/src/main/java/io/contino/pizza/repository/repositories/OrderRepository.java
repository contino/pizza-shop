package io.contino.pizza.repository.repositories;

import io.contino.transformer.models.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {

    private Map<String, Order> store = new HashMap<>();

    public void insertOrder(Order order){
        store.put(order.id(), order);
    }
}
