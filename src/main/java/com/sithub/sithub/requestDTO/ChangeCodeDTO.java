package com.sithub.sithub.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeCodeDTO {
    private String  roomId;

    private String code;

    private int line;

}
