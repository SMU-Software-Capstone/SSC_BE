package com.sithub.sithub.responseDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SnapshotDTO{
    private String roomId;
    private String fileName;
    private List<List<String>> code;
}
