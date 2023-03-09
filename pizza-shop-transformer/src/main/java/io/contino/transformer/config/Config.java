package io.contino.transformer.config;

import brave.Tracing;
import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig;
import brave.context.slf4j.MDCScopeDecorator;
import brave.kafka.streams.KafkaStreamsTracing;
import brave.propagation.CurrentTraceContext;
import io.contino.transformer.models.Order;
import io.contino.transformer.topologies.TopologyService;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@EnableKafka
public class Config {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    String bootstrapAddress;

    @Value("${spring.application.name}")
    String applicationName;

    @Bean
    KafkaStreams kafkaStreams(TopologyService topologyService, KafkaStreamsTracing kafkaStreamsTracing) {
        Properties props = new Properties();
        props.put(APPLICATION_ID_CONFIG, applicationName);
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        var kafkaStreams = new KafkaStreams(topologyService.topology(), props, kafkaStreamsTracing.kafkaClientSupplier());
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        kafkaStreams.start();
        return kafkaStreams;
    }

    @Bean
    Serde<String> stringSerdes(){
        return Serdes.String();
    }

    @Bean
    Serde<Order> OrderSerdes(){
        return new JsonSerde<>();
    }

    @Bean
    KafkaStreamsTracing kafkaStreamsTracing(Tracing tracing) {
        return KafkaStreamsTracing.create(tracing);
    }
}
