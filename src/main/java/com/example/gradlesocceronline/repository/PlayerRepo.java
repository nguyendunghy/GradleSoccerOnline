package com.example.gradlesocceronline.repository;

import com.example.gradlesocceronline.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepo extends JpaRepository<Player,Long> {
        List<Player> getPlayerByTeamIdAndStatusOrderById(Long teamId, Long status);

}
