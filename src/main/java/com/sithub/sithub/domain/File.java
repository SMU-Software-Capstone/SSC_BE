package com.sithub.sithub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {
    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    private Manage manage;

    public void setManage(Manage manage) {
        this.manage = manage;

        if(!manage.getFiles().contains(this)) {
            manage.addFile(this);
        }
    }

    public File(String name) {
        this.name = name;
    }
}
