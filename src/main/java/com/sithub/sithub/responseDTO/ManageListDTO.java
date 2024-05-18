package com.sithub.sithub.responseDTO;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ManageListDTO {
    private Long manageId;
    private String comment;
    private LocalDateTime createDate;

    public ManageListDTO(Long manageId, String comment, LocalDateTime createDate) {
        this.manageId = manageId;
        this.comment = comment;
        this.createDate = createDate;
    }
}
