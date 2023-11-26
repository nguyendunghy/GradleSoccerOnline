package com.example.gradlesocceronline.model;

import com.example.gradlesocceronline.utils.Constants;
import com.example.gradlesocceronline.utils.PasswordUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Slf4j
@Table(name = "token")
public class Token {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String rawToken;

    @Column
    @JsonIgnore
    private Long status;

    @Column
    @JsonIgnore
    private Date createdAt;

    @Column
    @JsonIgnore
    private Date updatedAt;

    @JsonIgnore
    @Transient
    private String plainToken;
    @JsonIgnore
    @Transient
    private String[] tokenData;

    private static final int USER_ID_IDX = 0;
    private static final int TTL_IDX = 1;

    private static final int TEAM_ID_IDX = 2;
    private static final String SEPARATOR = "#";


    public Token(String tokenRaw) {
        this.rawToken = tokenRaw;
        this.plainToken = PasswordUtils.decrypt(tokenRaw);
        this.tokenData = plainToken.split(SEPARATOR);
    }

    public boolean validate() {
        if (StringUtils.isEmpty(rawToken)) {
            return false;
        }
        if (StringUtils.isEmpty(plainToken)) {
            this.plainToken = PasswordUtils.decrypt(rawToken);
            this.tokenData = plainToken.split(SEPARATOR);
        }

        return this.plainToken != null && this.tokenData != null
                && tokenData.length == 3
                && status != null && status.equals(Constants.ACTIVE)
                && StringUtils.isNumeric(tokenData[TTL_IDX])
                && System.currentTimeMillis() < Long.parseLong(tokenData[TTL_IDX]);

    }


    public static Token gen(User user) {
        String plainToken = user.getId() + SEPARATOR
                + (System.currentTimeMillis() + 60 * 1000 * Constants.TOKEN_TTL) + SEPARATOR
                + user.getTeamId();
        String rawToken = PasswordUtils.encrypt(plainToken);
        Token token = new Token(rawToken);
        token.setCreatedAt(new Date());
        token.setUpdatedAt(new Date());
        token.setStatus(Constants.ACTIVE);
        return token;
    }


    public String getRawToken() {
        return rawToken;
    }

    @JsonIgnore
    public Long getUserId() {
        try {
            return Long.valueOf(tokenData[USER_ID_IDX]);
        } catch (Exception e) {
            log.error("error: ", e);
        }
        return null;
    }

    @JsonIgnore
    public Long getTeamId() {
        try {
            return Long.valueOf(tokenData[TEAM_ID_IDX]);
        } catch (Exception e) {
            log.error("error: ", e);
        }
        return null;
    }
}
