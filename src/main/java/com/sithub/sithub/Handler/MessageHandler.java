package com.sithub.sithub.Handler;

import com.sithub.sithub.Service.RoomService;
import com.sithub.sithub.config.KafkaConstants;
import com.sithub.sithub.config.Util;
import com.sithub.sithub.requestDTO.ChangeCodeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageHandler {

    @Value("${jwt.secret}")
    private String secretKey;
    @Autowired
    private KafkaTemplate<String, ChangeCodeDTO> kafkaTemplate;
    private final Util util;
    private final RoomService roomService;

    @MessageMapping("/message")
    public void greeting(ChangeCodeDTO code) throws Exception {
        kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, code).get();
    }

    @MessageMapping("/exit")
    public void exit(@CookieValue String token, String roomId) throws Exception{
        String id = util.getUserStringId(token, secretKey);
        roomService.removeUser(id, roomId);
    }
}