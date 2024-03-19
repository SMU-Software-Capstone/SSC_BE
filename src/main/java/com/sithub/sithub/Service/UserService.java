package com.sithub.sithub.Service;

import com.sithub.sithub.Repository.UserRepository;
import com.sithub.sithub.config.Util;
import com.sithub.sithub.domain.User;
import com.sithub.sithub.requestDTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.regex.Pattern.matches;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final Util util;

    @Value("${jwt.secret}")
    private String secretKey;

    @Transactional
    public void join(User user){
        userRepository.save(user);
    }
    public String createToken(String id){
        return util.createJwt(id, secretKey);
    }

    public String login(UserDTO userDTO){
        Optional<User> member = userRepository.findByUserId(userDTO.getUserId());
        // 1. Id가 틀린 경우
        if(!member.isPresent()) return "Email Not Found";

        // 2. Pw가 틀린 경우
        User user = member.get();

        // 사용자가 입력한 비밀번호 (rawPassword)와 암호화된 비밀번호 (hashedPassword)를 비교
        if(!matches(userDTO.getPassword(), user.getPassword())) return "Password Not Equal";

        String id = user.getUserId();
        return Util.createJwt(id, secretKey);
    }
}
