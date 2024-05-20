package com.sithub.sithub.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSnapshotDTO {
    private String roomId;

    private String projectName;

    private String fileName;
}
