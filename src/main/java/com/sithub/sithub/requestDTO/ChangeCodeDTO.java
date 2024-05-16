package com.sithub.sithub.requestDTO;

import com.sithub.sithub.responseDTO.LineDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ChangeCodeDTO {
    private String nickname;

    private String teamName;

    private String projectName;

    private String code;

    private String fileName;

    private int line;

    private int start;

    private int end;

    private String updateType;
}
