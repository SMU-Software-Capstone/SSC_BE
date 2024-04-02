package com.sithub.sithub.Service;

import com.sithub.sithub.Repository.UserRepository;
import com.sithub.sithub.config.Util;
import com.sithub.sithub.domain.User;
import com.sithub.sithub.requestDTO.UserDTO;
import com.sithub.sithub.requestDTO.loginDTO;
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
    public Long join(UserDTO userDTO){
        User user = new User(userDTO.getUserId(), userDTO.getNickname(), userDTO.getPassword());
        userRepository.save(user);
        return user.getId();
    }
    public String createToken(Long id, String stringId){
        return util.createJwt(id, stringId, secretKey);
    }

    public String login(loginDTO loginDTO){
        Optional<User> member = userRepository.findByUserStringId(loginDTO.getUserId());
        // 1. Id가 틀린 경우
        if(!member.isPresent()) return "Email Not Found";

        // 2. Pw가 틀린 경우
        User user = member.get();

        // 사용자가 입력한 비밀번호 (rawPassword)와 암호화된 비밀번호 (hashedPassword)를 비교
        if(!matches(loginDTO.getPassword(), user.getPassword())) return "Password Not Equal";

        return Util.createJwt(user.getId(), user.getUserStringId(), secretKey);
    }
}
