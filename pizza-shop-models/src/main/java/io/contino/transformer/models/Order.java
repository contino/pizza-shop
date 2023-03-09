package io.contino.transformer.models;

import lombok.With;

public record Order (@With String id, @With String customerId, @With String pizza, @With Address address){}
