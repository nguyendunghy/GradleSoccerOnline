package com.example.gradlesocceronline.model;


import com.example.gradlesocceronline.model.enums.PlayerType;
import com.example.gradlesocceronline.model.request.EditPlayerRequest;
import com.example.gradlesocceronline.utils.Constants;
import com.example.gradlesocceronline.utils.RandomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String country;

    @Column
    private Long age;

    @Column
    private Long marketValue;

    @Column
    @Enumerated(EnumType.STRING)
    private PlayerType type;

    @Column
    private Long teamId;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private Long status;

    public static Player getInstance(PlayerType playerType, Long teamId) {
        Player player = Player.builder()
                .firstName(RandomUtils.randomFirstName())
                .lastName(RandomUtils.randomLastName())
                .age((long) RandomUtils.random(18, 40))
                .country(RandomUtils.randomCountryCode())
                .marketValue(Constants.INIT_PLAY_VALUE)
                .createdAt(new Date())
                .updatedAt(new Date())
                .status(Constants.ACTIVE)
                .type(playerType)
                .teamId(teamId)
                .build();
        return player;
    }

    public Player update(EditPlayerRequest request) {
        Player player = Player.builder()
                .id(this.id)
                .firstName(this.firstName).lastName(this.lastName).age(this.age)
                .country(this.country).marketValue(this.marketValue).type(this.type)
                .teamId(this.teamId).createdAt(this.createdAt).updatedAt(new Date())
                .status(this.status)
                .build();

        if (request == null) {
            return player;
        }

        if (StringUtils.isNotEmpty(request.getFirstName())) {
            player.setFirstName(request.getFirstName());
        }

        if (StringUtils.isNotEmpty(request.getLastName())) {
            player.setLastName(request.getLastName());
        }

        if (request.getAge() != null) {
            player.setAge(request.getAge());
        }

        if (request.getTeamId() != null) {
            player.setTeamId(request.getTeamId());
        }

        if (request.getMarketValue() != null) {
            player.setMarketValue(request.getMarketValue());
        }

        if (StringUtils.isNotEmpty(request.getCountry())) {
            player.setCountry(request.getCountry());
        }

        if (request.getStatus() != null) {
            player.setStatus(request.getStatus());
        }

        return player;
    }

}
