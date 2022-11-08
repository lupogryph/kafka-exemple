package fr.uge.consumer;

import fr.uge.config.KConfig;
import fr.uge.data.JsonDeserializer;
import fr.uge.data.JsonSerializer;
import fr.uge.data.RecordGenerator;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;

public class KStreamConsumer {

    public static void main(String[] args) {
        final Properties props = new Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                KConfig.BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.CLIENT_ID_CONFIG,
                "KafkaStreamConsumer");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,
                "KafkaStreamConsumer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "KafkaStreamConsumer");

        StreamsConfig streamsConfig = new StreamsConfig(props);
        Serde<String> stringSerde = Serdes.String();
        Serde<RecordGenerator> recordSerde = Serdes.serdeFrom(new JsonSerializer(), new JsonDeserializer());
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, RecordGenerator> kStream = builder.stream(KConfig.TOPIC, Consumed.with(stringSerde, recordSerde));

        KStream<String, RecordGenerator> anonymousKStream = kStream.map((k, v) -> {
            v.setNom("**");
            v.setPrenom("**");
            return KeyValue.pair(k, v);
        });

        anonymousKStream.to("testout", Produced.with(stringSerde, recordSerde));

        final KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfig);
        kafkaStreams.start();
//        kafkaStreams.close();
    }
}
