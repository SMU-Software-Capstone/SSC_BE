package com.sithub.sithub.Handler;

import com.sithub.sithub.config.KafkaConstants;
import com.sithub.sithub.requestDTO.ChangeCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MessageHandler {

    @Autowired
    private KafkaTemplate<String, ChangeCodeDTO> kafkaTemplate;

    @MessageMapping("/message")
    public void greeting(ChangeCodeDTO code) throws Exception {
        kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, code).get();
    }
}