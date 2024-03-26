package com.sithub.sithub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String randomId;

    @OneToMany(mappedBy = "room")
    private List<User> userList;

    public void addUser(User user){
        this.userList.add(user);
    }


    public Room(String title, String randomId) {
        this.title = title;
        this.randomId = randomId;
    }
}
