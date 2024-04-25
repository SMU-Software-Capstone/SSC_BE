package com.sithub.sithub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    private Team team;

    @OneToMany(mappedBy = "project")
    private List<Manage> manages = new ArrayList<>();

    public void addManage(Manage manage) {
        this.manages.add(manage);
    }

    public void setTeam(Team team) {
        this.team = team;

        if(!team.getProjects().contains(this)) {
            team.addProject(this);
        }
    }
}
