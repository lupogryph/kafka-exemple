package fr.uge.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class JsonSerializer implements Serializer<RecordGenerator> {

    private static final ObjectMapper jsonMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    public static String serialize(RecordGenerator recordGenerator) {
        try {
            return jsonMapper.writeValueAsString(recordGenerator);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public byte[] serialize(String s, RecordGenerator recordGenerator) {
        try {
            return jsonMapper.writeValueAsBytes(recordGenerator);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, RecordGenerator data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
