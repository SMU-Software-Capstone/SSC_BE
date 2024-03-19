package com.sithub.sithub.controller;

import com.sithub.sithub.Service.UserService;
import com.sithub.sithub.domain.User;
import com.sithub.sithub.requestDTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDTO) {


        Long userId = userService.join(userDTO);

        String token = userService.createToken(userId);
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
    public String login(@RequestBody UserDTO userDTO){
        String token = userService.login(userDTO);

        if(token == "Email Not Found" || token == "Password Not Equal"){
            System.out.println("Not user" + token + " " + userDTO.getPassword());
            return "fail";
        }

        Cookie cookie = new Cookie("token", token);

        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 일
        cookie.setHttpOnly(true);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);

        if(userDTO.getUserId().equals("admin@sangmyung.kr")) {
            return "admin";
        }

        return "success";
    }

}
