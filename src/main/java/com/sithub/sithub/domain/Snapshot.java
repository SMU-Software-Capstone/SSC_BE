package com.sithub.sithub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.SecondaryTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "snapshots")
@Getter @Setter
@NoArgsConstructor
public class Snapshot {
    @Id
    private String id;

    public Snapshot(String roomId, String fileName, List<String> code, String contentType) {
        this.roomId = roomId;
        this.fileName = fileName;
        this.code = code;
        this.contentType = contentType;
    }

    private String roomId;
    private String fileName;
    private String contentType;
    private List<String> code;


    public void updateCode(String newCode, int lineNumber) {
        if (lineNumber < 0 || lineNumber >= code.size()) {
            throw new IndexOutOfBoundsException("Invalid line number");
        }
        code.set(lineNumber, newCode);
    }



}
