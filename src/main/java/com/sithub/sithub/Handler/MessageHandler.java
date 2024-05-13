package com.sithub.sithub.Handler;

import com.sithub.sithub.Service.RoomService;
import com.sithub.sithub.config.KafkaConstants;
import com.sithub.sithub.config.Util;
import com.sithub.sithub.requestDTO.ChangeCodeDTO;
import com.sithub.sithub.responseDTO.RoomResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;

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
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message")
    public void greeting(ChangeCodeDTO code) throws Exception {
        System.out.println("code = " + code);
        kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, code).get();
    }

    @MessageMapping("/exit")
    public void exit(@CookieValue String token, String roomId){
        String id = util.getUserStringId(token, secretKey);
        roomService.removeUser(id, roomId);
    }

    @MessageMapping("/users/{roomId}")
    public void subscribeToUsers(@PathVariable String roomId){
        RoomResponseDTO roomResponseDTO = roomService.getRoomResponseDTO(roomId);
        messagingTemplate.convertAndSend("/topic/users/" + roomId, roomResponseDTO);
    }
}