package com.example.gradlesocceronline.controller;

import com.example.gradlesocceronline.model.Player;
import com.example.gradlesocceronline.model.request.EditPlayerRequest;
import com.example.gradlesocceronline.model.response.CommonResponse;
import com.example.gradlesocceronline.service.PlayerService;
import com.example.gradlesocceronline.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.gradlesocceronline.utils.Constants.REQUEST_ATTRIBUTE.TEAM_ID;

@RestController
@RequestMapping("/player")
@Slf4j
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PutMapping(value = "/{playerId}", consumes = "application/json", produces = "application/json")
    public CommonResponse editTeam(@RequestAttribute(name = TEAM_ID) Long tokenTeamId,
                                   @PathVariable("playerId") Long playerId,
                                   @RequestBody EditPlayerRequest editPlayerRequest) {
        Player player = playerService.getById(playerId);
        if (player == null) {
            CommonResponse response = CommonResponse.builder()
                    .code(Constants.CODE.ERROR.EDIT_PLAYER)
                    .message(Constants.MESSAGE.PLAYER_NOT_FOUND)
                    .build();
            return response;
        }
        if (!tokenTeamId.equals(player.getTeamId())) {
            CommonResponse response = CommonResponse.builder()
                    .code(Constants.CODE.ERROR.NO_PERMISSION_EDIT_PLAYER)
                    .message(Constants.MESSAGE.NO_PERMISSION_EDIT_PLAYER)
                    .build();
            return response;
        }

        String errorMessage = playerService.edit(player, editPlayerRequest);
        if (StringUtils.isNotEmpty(errorMessage)) {
            CommonResponse response = CommonResponse.builder()
                    .code(Constants.CODE.ERROR.EDIT_PLAYER)
                    .message(errorMessage)
                    .build();
            return response;
        }

        return new CommonResponse(Constants.CODE.SUCCESS, Constants.MESSAGE.SUCCESS);
    }
}
