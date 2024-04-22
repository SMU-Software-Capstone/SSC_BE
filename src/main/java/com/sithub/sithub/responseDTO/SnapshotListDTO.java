package com.sithub.sithub.responseDTO;

import lombok.Getter;

@Getter
public class SnapshotListDTO {
    private String snapshotId;
    private String fileName;

    public SnapshotListDTO(String snapshotId, String fileName) {
        this.snapshotId = snapshotId;
        this.fileName = fileName;
    }
}
