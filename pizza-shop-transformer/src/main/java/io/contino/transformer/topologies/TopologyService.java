package io.contino.transformer.topologies;

import brave.kafka.streams.KafkaStreamsTracing;
import io.contino.transformer.models.Order;
import io.contino.transformer.properties.ServiceProperties;
import io.contino.transformer.transformers.OrderTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopologyService {

    private final ServiceProperties serviceProperties;
    private final KafkaStreamsTracing kafkaStreamsTracing;
    private final OrderTransformer transformer;
    private final Serde<String> stringSerde;
    private final Serde<Order> orderSerde;

    public Topology topology() {
        var streamsBuilder = new StreamsBuilder();
        KStream<String, String> messageStream = streamsBuilder
                .stream(serviceProperties.inbound().topic().topicName(), Consumed.with(stringSerde, stringSerde));

        messageStream.transformValues(kafkaStreamsTracing.valueTransformerWithKey(transformer.getClass().getSimpleName(), transformer))
                .to(serviceProperties.outbound().topic().topicName(), Produced.with(stringSerde, orderSerde));

        return streamsBuilder.build();
    }

}
