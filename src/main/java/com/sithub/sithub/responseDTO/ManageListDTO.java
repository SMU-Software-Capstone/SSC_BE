package com.sithub.sithub.responseDTO;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ManageListDTO {
    private Long manageId;
    private LocalDateTime createDate;

    public ManageListDTO(Long manageId, LocalDateTime createDate) {
        this.manageId = manageId;
        this.createDate = createDate;
    }
}
