package com.sithub.sithub.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class S3UploadDTO {
    private String teamName;

    private String projectName;

    private String comment;
}
