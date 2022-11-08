package fr.uge.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class JsonDeserializer implements Deserializer<RecordGenerator> {

    private static final ObjectMapper jsonMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    @Override
    public RecordGenerator deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public RecordGenerator deserialize(String s, byte[] json) {
        try {
            return jsonMapper.readValue(json, RecordGenerator.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
