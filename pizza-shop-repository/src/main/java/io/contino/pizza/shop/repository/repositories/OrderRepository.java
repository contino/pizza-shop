package io.contino.pizza.shop.repository.repositories;

import io.contino.pizza.shop.models.Order;
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
