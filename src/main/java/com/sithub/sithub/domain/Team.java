package com.sithub.sithub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Project> projects = new ArrayList<>();

    @ManyToMany(mappedBy = "teams")
    private List<User> users = new ArrayList<>();


    public void addUser(User user) {
        this.users.add(user);
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public Team(String name) {
        this.name = name;
    }
}
