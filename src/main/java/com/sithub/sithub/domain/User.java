package com.sithub.sithub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;

    private String userStringId;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @ManyToMany
    @JoinTable(name = "user_team",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams = new ArrayList<>();

    public void setRoom(Room room) {
        this.room = room;

        if(!room.getUserList().contains(this)) {
            room.getUserList().add(this);
        }
    }

    public void addTeam(Team team) {
        this.teams.add(team);

        if(!team.getUsers().contains(this)) {
            team.addUser(this);
        }
    }

    public void removeUser(Room room) {
        this.room = room;

        if(room.getUserList().contains(this)) {
            room.getUserList().remove(this);
        }
    }

    public User(String userStringId, String nickname, String password) {
        this.userStringId = userStringId;
        this.nickname = nickname;
        this.password = password;
    }
}

