package com.sithub.sithub.config;

import com.sithub.sithub.Service.SnapshotService;
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

    private final SnapshotService snapshotService;

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = "${kafka.group.id:${random.uuid}}")
    public void checkNotice(ChangeCodeDTO code){
        try{
            snapshotService.updateCodes(code.getTeamName(), code.getUpdateType(), code.getCode(), code.getFileName(), code.getProjectName(), code.getLine());
            messagingTemplate.convertAndSend(
                    "/subscribe/notice/" + code.getTeamName() + "/" + code.getFileName(),
                    SendCodeDTO.of(code));
        }catch(Exception ex){
            log.error(ex.getMessage());
        }
    }
}