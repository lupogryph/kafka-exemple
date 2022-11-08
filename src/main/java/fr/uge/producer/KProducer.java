package fr.uge.producer;

import fr.uge.data.JsonSerializer;
import fr.uge.data.RecordGenerator;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static fr.uge.config.KConfig.BOOTSTRAP_SERVERS;
import static fr.uge.config.KConfig.TOPIC;

public class KProducer {

    private static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    public static void start(final int nb) throws ExecutionException, InterruptedException {
        final Producer<Long, String> producer = createProducer();
        long time = System.currentTimeMillis();

        try {
            for (long index = time; index < time + nb; index++) {
                final ProducerRecord<Long, String> record =
                        new ProducerRecord<>(TOPIC, index, JsonSerializer.serialize(new RecordGenerator()));

                RecordMetadata metadata = producer.send(record).get();

                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);
            }
        } finally {
            producer.flush();
            producer.close();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        start(100);
    }
}
