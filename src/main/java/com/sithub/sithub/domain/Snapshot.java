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
    private String roomId;
    private String fileName;
    private List<List<String>> code; //line list 자료구조로 바꿔야함.

    public void updateCode(String newCode, int lineNumber) {
        if (lineNumber < 0 || lineNumber >= code.size()) {
            throw new IndexOutOfBoundsException("Invalid line number");
        }
        List<String> line = code.get(lineNumber);
        line.clear();
        line.add(newCode);
        code.set(lineNumber, line);
    }



}