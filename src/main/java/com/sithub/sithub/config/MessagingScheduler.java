package com.sithub.sithub.config;

import com.sithub.sithub.requestDTO.ChangeCodeDTO;
import com.sithub.sithub.responseDTO.SendCodeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagingScheduler {
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = "${kafka.group.id:${random.uuid}}")
    public void checkNotice(ChangeCodeDTO code){
        log.info("checkNotice call");
        System.out.println("message = " + code);
        try{
            messagingTemplate.convertAndSend("/subscribe/notice/" + code.getRoomId(), SendCodeDTO.of(code));
        }catch(Exception ex){
            log.error(ex.getMessage());
        }
    }
}