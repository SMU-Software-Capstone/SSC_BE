package com.sithub.sithub.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnapshotRequestDTO {
    private String roomId;
    private String fileName;
    private String code;

}