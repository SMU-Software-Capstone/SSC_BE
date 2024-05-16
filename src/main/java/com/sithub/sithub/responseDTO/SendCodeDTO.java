package com.sithub.sithub.responseDTO;

import com.sithub.sithub.requestDTO.ChangeCodeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SendCodeDTO {

    private String nickname;

    private String code;

    private String type;

    private int line;

    public SendCodeDTO(String nickname, String code, String type, int line) {
        this.code = code;
        this.line = line;
        this.type = type;
        this.nickname = nickname;
    }

    public static SendCodeDTO of(ChangeCodeDTO code) {
        return new SendCodeDTO(
                code.getNickname(),
                code.getCode(),
                code.getUpdateType(),
                code.getLine()
        );
    }
}
