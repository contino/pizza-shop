package io.contino.pizza.shop.transformer.config;

import brave.Tracing;
import brave.kafka.streams.KafkaStreamsTracing;
import io.contino.pizza.shop.transformer.properties.ServiceProperties;
import io.contino.pizza.shop.transformer.topologies.TopologyService;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;

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
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        var kafkaStreams = new KafkaStreams(topologyService.topology(), props, kafkaStreamsTracing.kafkaClientSupplier());
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        kafkaStreams.start();
        return kafkaStreams;
    }

    @Bean
    KafkaStreamsTracing kafkaStreamsTracing(Tracing tracing) {
        return KafkaStreamsTracing.create(tracing);
    }

    @Bean
    NewTopic inputTopic(ServiceProperties serviceProperties) {
        return TopicBuilder.name(serviceProperties.inbound().topic().topicName()).build();
    }

    @Bean
    NewTopic outputTopic(ServiceProperties serviceProperties) {
        return TopicBuilder.name(serviceProperties.outbound().topic().topicName()).build();
    }
}
