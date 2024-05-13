package com.sithub.sithub.requestDTO;

import lombok.Getter;

@Getter
public class ChangeSnapshotDTO {
    private Long commitId;

    private String teamName;

    private String projectName;
}
