package com.example.gradlesocceronline.repository;

import com.example.gradlesocceronline.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepo extends JpaRepository<Team,Long> {
    List<Team> getTeamByIdAndStatus(Long id, Long status);

}
