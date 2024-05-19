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

    public Snapshot(String roomId, String fileName, List<String> code, String contentType, String projectName) {
        this.roomId = roomId;
        this.fileName = fileName;
        this.code = code;
        this.contentType = contentType;
        this.projectName = projectName;
    }

    public Snapshot(String roomId, String fileName) {
        this.roomId = roomId;
        this.fileName = fileName;
    }

    private String roomId;

    private String projectName;

    private String fileName;

    private String contentType;

    private List<String> code;

    public void updateCode(String updateType, List<String> newCode, int lineNumber) {
        if (lineNumber < 0 || lineNumber > code.size()) {
            throw new IndexOutOfBoundsException("Invalid line number");
        }

        if (updateType.equals("create")) {
            //새로운 라인 생성
            if (lineNumber == code.size() - 1) {
                code.add(newCode.get(0));
            } else {    //중간값에 넣기
                code.set(lineNumber - 1, newCode.get(0));
                code.add(lineNumber, newCode.get(1));
            }
        } else if (updateType.equals("delete")) {
            code.set(lineNumber - 1, code.get(lineNumber - 1) + code.get(lineNumber));
            code.remove(lineNumber);
        } else if (updateType.equals("update")) {
            code.set(lineNumber, newCode.get(0));
        } else if (updateType.equals("paste")) {
            code.set(lineNumber, code.get(lineNumber) + newCode.get(0));

            for(int i = 1; i < newCode.size(); i++) {
                code.add(lineNumber + i, newCode.get(i));
            }
        }
        else {
            throw new IllegalArgumentException("Invalid update type");
        }
    }
}
