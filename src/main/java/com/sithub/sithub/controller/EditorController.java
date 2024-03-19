package com.sithub.sithub.controller;

import com.sithub.sithub.Service.EditorService;
import com.sithub.sithub.config.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/editor")
public class EditorController {
    private final EditorService editorService;
    private final Util util;

    @Value("${jwt.secret}")
    private String secretKey;
    @GetMapping("/connect")
    public String editor() {
        return UUID.randomUUID().toString();
    }

    @GetMapping("/create")
    public String createRoom(@CookieValue String token, @RequestParam String title){
        Long id = util.getUserId(token, secretKey);
        editorService.create(title, id);
        return "Success";
    }

    @GetMapping("/editor/{roomId}")
    public String entryEditor(@PathVariable String roomId){
        //여기 들어가면서 방정보 불러와야함.
        return "Success";
    }
}
