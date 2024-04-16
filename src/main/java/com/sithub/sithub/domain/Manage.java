package com.sithub.sithub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manage extends BaseTime{
    @Id
    @Column(name = "manage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long name;

    private String comment;

    @ManyToOne(fetch = LAZY)
    private Team team;

    public void setTeam(Team team) {
        this.team = team;
    }
}
