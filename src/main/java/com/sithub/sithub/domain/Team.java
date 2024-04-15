package com.sithub.sithub.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Team {
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
