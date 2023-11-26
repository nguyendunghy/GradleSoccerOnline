package com.example.gradlesocceronline.service;

import com.example.gradlesocceronline.model.Team;
import com.example.gradlesocceronline.model.request.EditTeamRequest;
import com.example.gradlesocceronline.repository.TeamRepo;
import com.example.gradlesocceronline.utils.Constants;
import com.example.gradlesocceronline.utils.RandomUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TeamService {
    @Autowired
    private TeamRepo teamRepo;

    public Team save(Team team) {
        return teamRepo.save(team);
    }

    public Team getById(Long id) {
        List<Team> teamList = teamRepo.getTeamByIdAndStatus(id, Constants.ACTIVE);
        if (teamList == null || teamList.isEmpty()) {
            return null;
        }
        return teamList.get(0);
    }


    public String edit(Long teamId, EditTeamRequest request) {
        String errorMsg = validateEditTeamRequest(request);
        if (StringUtils.isNotEmpty(errorMsg)) {
            return errorMsg;
        }
        List<Team> teamList = teamRepo.getTeamByIdAndStatus(teamId, Constants.ACTIVE);
        if (teamList == null || teamList.isEmpty()) {
            return Constants.MESSAGE.TEAM_NOT_FOUND;
        }

        Team team = teamList.get(0).update(request);
        teamRepo.save(team);
        return null;
    }

    private String validateEditTeamRequest(EditTeamRequest request) {
        if(request == null){
            return Constants.MESSAGE.BAD_REQUEST;
        }

        return null;
    }


    public Team generate() {
        Team team = Team.builder()
                .country(RandomUtils.randomCountryCode())
                .name(RandomUtils.randomTeamName())
                .value(20 * 1000 * 1000L)
                .budget(5 * 1000 * 1000L)
                .createdAt(new Date())
                .status(Constants.ACTIVE)
                .updatedAt(new Date())
                .build();
        return team;
    }
}
