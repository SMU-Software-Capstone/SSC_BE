package com.sithub.sithub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manage extends BaseTime{
    @Id
    @Column(name = "manage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne(fetch = LAZY)
    private Team team;

    @OneToMany(mappedBy = "manage", cascade = {PERSIST})
    private List<File> files = new ArrayList<>();

    public void setTeam(Team team) {
        this.team = team;

        if(!team.getManages().contains(this)) {
            team.addManage(this);
        }
    }

    public void addFile(File file) {
        this.files.add(file);
    }

    public Manage(String comment) {
        this.comment = comment;
    }
}
