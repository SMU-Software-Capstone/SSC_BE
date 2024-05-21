package com.sithub.sithub.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class FileReloadDTO {
    private String teamName;

    private String projectName;
}
