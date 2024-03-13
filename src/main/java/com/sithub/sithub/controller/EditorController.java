package com.sithub.sithub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/editor")
public class EditorController {

    @GetMapping("/connect")
    public String editor() {
        return UUID.randomUUID().toString();
    }
}
