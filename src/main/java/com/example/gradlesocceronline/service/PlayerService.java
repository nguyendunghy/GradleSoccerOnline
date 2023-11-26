package com.example.gradlesocceronline.service;

import com.example.gradlesocceronline.model.Player;
import com.example.gradlesocceronline.model.request.EditPlayerRequest;
import com.example.gradlesocceronline.repository.PlayerRepo;
import com.example.gradlesocceronline.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class PlayerService {
    @Autowired
    private PlayerRepo playerRepo;

    public Player save(Player player) {
        return playerRepo.save(player);
    }

    public List<Player> getByTeamId(Long teamId, Long status) {
        return playerRepo.getPlayerByTeamIdAndStatusOrderById(teamId, status);
    }

    public Player getById(Long id) {
        Player player = playerRepo.findById(id).orElse(null);
        if (player == null) {
            return null;
        }
        if (!Constants.ACTIVE.equals(player.getStatus())) {
            return null;
        }

        return player;
    }

    public String edit(Player player, EditPlayerRequest editPlayerRequest) {
        String errorMessage = validateEditPlayerRequest(editPlayerRequest);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return errorMessage;
        }

        Player newPlayer = player.update(editPlayerRequest);
        playerRepo.save(newPlayer);
        return null;
    }


    private String validateEditPlayerRequest(EditPlayerRequest editPlayerRequest) {
        if (editPlayerRequest == null) {
            return Constants.MESSAGE.BAD_REQUEST;
        }

        return null;
    }
}
