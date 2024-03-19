package com.sithub.sithub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Editor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Editor(String title, String randomId, String userId) {
        this.title = title;
        this.randomId = randomId;
        this.userId = userId;
    }

    private String title;
    private String randomId;
    private String userId;


}
