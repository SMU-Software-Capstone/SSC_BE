package com.sithub.sithub.responseDTO;

import com.sithub.sithub.requestDTO.ChangeCodeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.sithub.sithub.requestDTO.ChangeCodeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SendCodeDTO {

    private String nickname;

    private List<String> code;

    private String type;

    private int line;

    private int cursorStart;

    private int cursorEnd;

    public SendCodeDTO(String nickname, List<String> code, String type, int line, int cursorStart, int cursorEnd) {
        this.nickname = nickname;
        this.code = code;
        this.type = type;
        this.line = line;
        this.cursorStart = cursorStart;
        this.cursorEnd = cursorEnd;
    }

    public static SendCodeDTO of(ChangeCodeDTO code) {
        return new SendCodeDTO(
                code.getNickname(),
                code.getCode(),
                code.getUpdateType(),
                code.getLine(),
                code.getStart(),
                code.getEnd()
        );
    }
}