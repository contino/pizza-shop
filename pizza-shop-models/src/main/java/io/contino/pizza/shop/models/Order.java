package io.contino.pizza.shop.models;

import lombok.With;

public record Order (@With String id, @With String customerId, @With String pizza, @With Address address){}
