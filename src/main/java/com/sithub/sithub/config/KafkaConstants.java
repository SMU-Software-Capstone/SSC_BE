package com.sithub.sithub.config;

import java.util.UUID;

public class KafkaConstants {
    public static final String KAFKA_TOPIC = "kafka-chat";
    public static final String GROUP_ID = UUID.randomUUID().toString();
    public static final String KAFKA_BROKER = "localhost:9092";
}
