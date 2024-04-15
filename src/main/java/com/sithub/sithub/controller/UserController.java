package com.sithub.sithub.controller;

import com.sithub.sithub.Service.UserService;
import com.sithub.sithub.requestDTO.UserDTO;
import com.sithub.sithub.requestDTO.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDTO) {


        Long userId = userService.join(userDTO);
        String stringId = userDTO.getUserId();

        String token = userService.createToken(userId, stringId);
        Cookie cookie = new Cookie("token", token);

        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(false);
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 일

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);

        return "Success";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO){
        String token = userService.login(loginDTO);

        if(token == "Email Not Found" || token == "Password Not Equal"){
            System.out.println("Not user" + token + " " + loginDTO.getPassword());
            return "fail";
        }

        Cookie cookie = new Cookie("token", token);

        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 일
        cookie.setHttpOnly(true);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);

        if(loginDTO.getUserId().equals("admin@sangmyung.kr")) {
            return "admin";
        }

        return "success";
    }

}
