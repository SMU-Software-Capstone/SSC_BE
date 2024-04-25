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
    private List<Manage> manages = new ArrayList<>();

    @ManyToMany(mappedBy = "teams")
    private List<User> users = new ArrayList<>();

    public void addManage(Manage manage) {
        this.manages.add(manage);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public Team(String name) {
        this.name = name;
    }
}
