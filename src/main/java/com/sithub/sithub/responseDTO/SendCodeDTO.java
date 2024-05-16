package com.sithub.sithub.responseDTO;

import com.sithub.sithub.requestDTO.ChangeCodeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SendCodeDTO {

    private String code;

    private int start;

    private int end;

    public SendCodeDTO(String code, int start, int end) {
        this.code = code;
        this.start = start;
        this.end = end;
    }

    public static SendCodeDTO of(ChangeCodeDTO code, String result) {
        return new SendCodeDTO(
                result,
                code.getStart(),
                code.getEnd()
        );
    }
}
