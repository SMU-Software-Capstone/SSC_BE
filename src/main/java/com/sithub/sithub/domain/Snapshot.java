package com.sithub.sithub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.SecondaryTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "snapshots")
@Getter @Setter
@NoArgsConstructor
public class Snapshot {
    @Id
    private String id;
    private String roomId;
    private String fileName;
    private String code;

    public void updateCode(String code) {
        this.code = code;
    }

}
