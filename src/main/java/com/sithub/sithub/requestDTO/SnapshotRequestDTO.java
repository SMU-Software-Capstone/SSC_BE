package com.sithub.sithub.requestDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SnapshotRequestDTO {
    private String roomId;
    private String fileName;
    private List<List<String>> code;

}