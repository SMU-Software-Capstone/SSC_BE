package com.sithub.sithub.Init;

import com.amazonaws.services.kms.model.NotFoundException;
import com.sithub.sithub.Repository.ManageRepository;
import com.sithub.sithub.Repository.ProjectRepository;
import com.sithub.sithub.Repository.TeamRepository;
import com.sithub.sithub.Repository.UserRepository;
import com.sithub.sithub.domain.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.TimeZone;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;

    private final ProjectRepository projectRepository;

    private final ManageRepository manageRepository;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        // 유저 데이터 생성
        User user1 = new User("user1", "user1", "1234");
        User user2 = new User("user2", "user2", "1234");
        User user3 = new User("user3", "user3", "1234");

        // 팀 데이터 생성
        Team team = new Team("team1");
        user1.addTeam(team);

        teamRepository.save(team);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // 프로젝트 데이터 생성
        Project project = new Project("sithub");
        project.setTeam(team);

        projectRepository.save(project);

        // 커밋 로그 데이터 생성
        String[] paths1 = {
                "team1-sithub20240428205719/folderTest/UserButton.js",
                "team1-sithub20240428205719/folderTest/UserInput.js",
                "team1-sithub20240428205719/folderTest/child/UserList.js"};

        Manage manage1 = new Manage("1차 수정");
        manage1.setProject(project);

        for (String path : paths1) {
            File file = new File(path);
            file.setManage(manage1);
        }

        String[] paths2 = {
                "team1-sithub20240425221350/folderTest/UserButton.js",
                "team1-sithub20240425221350/folderTest/UserInput.js",
                "team1-sithub20240425221350/folderTest/child/UserList.js"};

        Manage manage2 = new Manage("2차 수정");
        manage2.setProject(project);

        for (String path : paths2) {
            File file = new File(path);
            file.setManage(manage2);
        }
        manageRepository.save(manage1);
        manageRepository.save(manage2);


    }
}
