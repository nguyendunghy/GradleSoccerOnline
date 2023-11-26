package com.example.gradlesocceronline.controller;

import com.example.gradlesocceronline.model.Player;
import com.example.gradlesocceronline.model.Team;
import com.example.gradlesocceronline.model.request.EditTeamRequest;
import com.example.gradlesocceronline.model.response.CommonResponse;
import com.example.gradlesocceronline.service.PlayerService;
import com.example.gradlesocceronline.service.TeamService;
import com.example.gradlesocceronline.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.gradlesocceronline.utils.Constants.REQUEST_ATTRIBUTE.TEAM_ID;


@RestController
@RequestMapping("/team")
@Slf4j
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    @GetMapping(value = "/", consumes = "application/json", produces = "application/json")
    public CommonResponse getTeam(@RequestAttribute(name = TEAM_ID) Long teamId) {

        Team team = teamService.getById(teamId);

        List<Player> playerList = playerService.getByTeamId(teamId, Constants.ACTIVE);

        team.setPlayerList(playerList);

        return new CommonResponse(Constants.CODE.SUCCESS, Constants.MESSAGE.SUCCESS, team);
    }

    @PutMapping(value = "/{teamId}", consumes = "application/json", produces = "application/json")
    public CommonResponse editTeam(@RequestAttribute(name = TEAM_ID) Long tokenTeamId,
                                  @PathVariable("teamId") Long teamId,
                                  @RequestBody EditTeamRequest editTeamRequest) {
        if (!tokenTeamId.equals(teamId)) {
            CommonResponse response = CommonResponse.builder()
                    .code(Constants.CODE.ERROR.NO_PERMISSION_EDIT_TEAM)
                    .message(Constants.MESSAGE.NO_PERMISSION_EDIT_TEAM)
                    .build();
            return response;
        }

        String errorMessage = teamService.edit(teamId, editTeamRequest);
        if(StringUtils.isNotEmpty(errorMessage)){
            CommonResponse response = CommonResponse.builder()
                    .code(Constants.CODE.ERROR.EDIT_TEAM)
                    .message(errorMessage)
                    .build();
            return response;
        }

        return new CommonResponse(Constants.CODE.SUCCESS, Constants.MESSAGE.SUCCESS);
    }
}
