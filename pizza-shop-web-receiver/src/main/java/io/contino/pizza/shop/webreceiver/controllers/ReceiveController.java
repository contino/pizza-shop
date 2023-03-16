package io.contino.pizza.shop.webreceiver.controllers;

import brave.baggage.BaggageField;
import io.contino.pizza.shop.webreceiver.IdGenerator;
import io.contino.pizza.shop.webreceiver.services.Sender;
import io.contino.pizza.shop.models.Order;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReceiveController {

    @NonNull
    private final Sender sender;

    @NonNull
    private final IdGenerator idGenerator;

    @NonNull
    @Qualifier("orderId")
    private final BaggageField orderIdBaggageField;

    @NonNull
    @Qualifier("customerId")
    private final BaggageField customerIdBaggageField;

    @PostMapping("${service.api.path}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void sendMessage(@RequestBody Order request) {
        var order = request.withId(idGenerator.generate());
        orderIdBaggageField.updateValue(order.id());
        customerIdBaggageField.updateValue(order.customerId());
        sender.send(order);
        log.info("order received");
    }
}
