package com.sithub.sithub.Service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.sithub.sithub.Repository.TeamRepository;
import com.sithub.sithub.Repository.UserRepository;
import com.sithub.sithub.domain.Team;
import com.sithub.sithub.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    @Transactional
    public String  createTeam(Long userId, String teamName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        if(teamRepository.findTeamByName(teamName).isPresent()) {
            return "fail";
        }

        Team team = new Team(teamName);
        teamRepository.save(team);

        user.addTeam(team);
        return "success";
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

        if(findTeam.get().getUsers().contains(findUser.get())) {
            return "Already Exist";
        }

        findUser.get().addTeam(findTeam.get());
        return "success";
    }

    public List<String> teamList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        List<String> result = new ArrayList<>();

        for(Team team : user.getTeams()) {
            result.add(team.getName());
        }

        return result;
    }

    public List<String> userList(String teamName) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + teamName));

        List<User> users = team.getUsers();
        List<String> result = new ArrayList<>();

        for (User user : users) {
            result.add(user.getNickname());
        }

        return result;
    }
}
