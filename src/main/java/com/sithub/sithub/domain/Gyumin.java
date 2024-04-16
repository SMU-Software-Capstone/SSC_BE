package com.sithub.sithub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Gyumin {

    @Id
    @Column(name = "Mccc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;
    private String fileName;
    private String code;

    public Gyumin(String roomId, String fileName, String code) {
        this.roomId = roomId;
        this.fileName = fileName;
        this.code = code;
    }

    public void updateCode(String newCode) {
        this.code = newCode;
    }
}
