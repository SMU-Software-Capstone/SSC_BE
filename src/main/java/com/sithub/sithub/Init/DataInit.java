package com.sithub.sithub.Init;

import com.sithub.sithub.Repository.TeamRepository;
import com.sithub.sithub.Repository.UserRepository;
import com.sithub.sithub.domain.Team;
import com.sithub.sithub.domain.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;

    @PostConstruct
    public void init() {
//        User user1 = new User("user1", "user1", "1234");
//        User user2 = new User("user2", "user2", "1234");
//        User user3 = new User("user3", "user3", "1234");
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
    }
}
