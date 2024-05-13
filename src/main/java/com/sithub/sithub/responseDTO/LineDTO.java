package com.sithub.sithub.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class LineDTO {
    private int line;

    private int start;

    private int end;
}
