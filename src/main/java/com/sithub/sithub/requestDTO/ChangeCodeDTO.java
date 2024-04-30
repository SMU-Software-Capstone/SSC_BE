package com.sithub.sithub.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ChangeCodeDTO {
    private String teamName;

    private String code;

    private String fileName;

    private int line;
}
