package com.sithub.sithub.Repository;

import com.sithub.sithub.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p from Project p " +
            "where p.team.id = :teamId")
    List<Project> findProjectsByTeamId(@Param("teamId") Long teamId);

    @Query("select p from Project p " +
            "where p.team.name = :teamName and p.name = :name")
    Optional<Project> findByTeamNameAndName(@Param("teamName") String teamName,
                                                   @Param("name") String name);
}
