package com.sithub.sithub.Repository;

import com.sithub.sithub.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findFilesByManageId(@Param("manageId") Long manageId);
}
