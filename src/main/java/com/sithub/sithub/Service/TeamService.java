package com.sithub.sithub.Service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.sithub.sithub.Repository.TeamRepository;
import com.sithub.sithub.Repository.UserRepository;
import com.sithub.sithub.domain.Team;
import com.sithub.sithub.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    @Transactional
    public void createTeam(Long userId, String teamName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        Team team = new Team(teamName);
        teamRepository.save(team);

        user.addTeam(team);
    }

    @Transactional
    public String addUser(String teamName, String nickname) {
        Optional<User> findUser = userRepository.findUserByNickname(nickname);

        if(!findUser.isPresent()) {
            return "Not Found User";
        }

        Optional<Team> findTeam = teamRepository.findTeamByName(teamName);

        if(!findTeam.isPresent()) {
            return "Not Found Team";
        }

        findUser.get().addTeam(findTeam.get());
        return "success";
    }
}
