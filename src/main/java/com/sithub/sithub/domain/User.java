package com.sithub.sithub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String userId;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    public void setRoom(Room room) {
        this.room = room;

        if(!room.getUserList().contains(this)) {
            room.getUserList().add(this);
        }
    }

    public void removeUser(Room room) {
        this.room = room;

        if(room.getUserList().contains(this)) {
            room.getUserList().remove(this);
        }
    }

    public User(String userId, String nickname, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
    }
}

