package com.sithub.sithub.controller;

import com.sithub.sithub.Service.RoomService;
import com.sithub.sithub.config.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/editor")
public class RoomController {
    private final RoomService editorService;
    private final Util util;

    @Value("${jwt.secret}")
    private String secretKey;
    @GetMapping("/connect")
    public String editor() {
        return UUID.randomUUID().toString();
    }

    @GetMapping("/create")
    public String createRoom(@CookieValue String token){
        Long id = util.getUserId(token, secretKey);
        editorService.create(id);
        return "Success";
    }

    @PostMapping("/editor/{roomId}")
    public void enterEditor(@CookieValue String token, @PathVariable String roomId){
        String id = util.getUserStringId(token, secretKey);
        editorService.getRoomAndSaveUser(id, roomId);
    }
}
