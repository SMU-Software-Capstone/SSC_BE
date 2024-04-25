package com.sithub.sithub.Repository;

import com.sithub.sithub.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
