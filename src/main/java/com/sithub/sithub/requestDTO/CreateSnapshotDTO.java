package com.sithub.sithub.requestDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateSnapshotDTO {
    private String roomId;

    private String projectName;

    private String fileName;
}
