package io.contino.pizza.shop.transformer.topologies;

import brave.kafka.streams.KafkaStreamsTracing;
import io.contino.pizza.shop.models.Order;
import io.contino.pizza.shop.transformer.properties.ServiceProperties;
import io.contino.pizza.shop.transformer.transformers.OrderProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopologyService {

    private final ServiceProperties serviceProperties;
    private final KafkaStreamsTracing kafkaStreamsTracing;
    private final OrderProcessor processor;

    public Topology topology() {
        var streamsBuilder = new StreamsBuilder();
        KStream<String, Order> messageStream = streamsBuilder.stream(serviceProperties.inbound().topic().topicName());

        messageStream.processValues(kafkaStreamsTracing.processValues(OrderProcessor.class.getSimpleName(), processor))
                .to(serviceProperties.outbound().topic().topicName());

        return streamsBuilder.build();
    }
}
