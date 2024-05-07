package com.sithub.sithub.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeDTO {
    private String updateType;
    private int lineNumber;
    private String code;

}
