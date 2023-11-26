package com.example.gradlesocceronline.model;

import com.example.gradlesocceronline.model.request.EditTeamRequest;
import com.example.gradlesocceronline.utils.Constants;
import com.example.gradlesocceronline.utils.RandomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "team")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String country;

    @Column
    private Long value;

    @Column
    private Long budget;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private Long status;

    @Transient
    private List<Player> playerList;

    public static Team getInstance(){
        Team team = Team.builder()
                .country(RandomUtils.randomCountryCode())
                .name(RandomUtils.randomTeamName())
                .value(Constants.INIT_TEAM_VALUE)
                .budget(Constants.INIT_TEAM_BUDGET)
                .status(Constants.ACTIVE)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return team;
    }

    public Team update(EditTeamRequest request){
        Team team = Team.builder()
                .id(this.id)
                .name(this.name)
                .budget(this.budget)
                .value(this.value)
                .country(this.country)
                .createdAt(this.createdAt)
                .updatedAt(new Date())
                .status(this.status)
                .build();
        if(request == null){
            return team;
        }

        if(StringUtils.isNotEmpty(request.getName())){
            team.setName(request.getName());
        }

        if(StringUtils.isNotEmpty(request.getCountry())){
            team.setCountry(request.getCountry());
        }

        if(request.getStatus() != null){
            team.setStatus(request.getStatus());
        }

        if(request.getBudget() != null){
            team.setBudget(request.getBudget());
        }

        return team;
    }

}
