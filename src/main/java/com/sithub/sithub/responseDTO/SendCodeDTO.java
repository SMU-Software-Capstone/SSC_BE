package com.sithub.sithub.responseDTO;

import com.sithub.sithub.requestDTO.ChangeCodeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SendCodeDTO {

    private String code;

    private int line;

    public SendCodeDTO(String code, int line) {
        this.code = code;
        this.line = line;
    }

    public static SendCodeDTO of(ChangeCodeDTO code) {
        return new SendCodeDTO(
                code.getCode(),
                code.getLine()
        );
    }
}
