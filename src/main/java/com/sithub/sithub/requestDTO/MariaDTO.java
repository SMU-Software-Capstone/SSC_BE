package com.sithub.sithub.requestDTO;

import lombok.Getter;
import org.springframework.web.service.annotation.GetExchange;

@Getter
public class MariaDTO {
    private String roomId;
    private String code;
}
