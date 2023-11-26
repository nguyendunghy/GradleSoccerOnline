package com.example.gradlesocceronline.service;


import com.example.gradlesocceronline.model.Player;
import com.example.gradlesocceronline.model.Team;
import com.example.gradlesocceronline.model.Token;
import com.example.gradlesocceronline.model.User;
import com.example.gradlesocceronline.model.enums.PlayerType;
import com.example.gradlesocceronline.model.request.LoginRequest;
import com.example.gradlesocceronline.model.request.RegisterRequest;
import com.example.gradlesocceronline.model.response.CommonResponse;
import com.example.gradlesocceronline.repository.PlayerRepo;
import com.example.gradlesocceronline.repository.TeamRepo;
import com.example.gradlesocceronline.repository.TokenRepo;
import com.example.gradlesocceronline.repository.UserRepo;
import com.example.gradlesocceronline.utils.Constants;
import com.example.gradlesocceronline.utils.PasswordUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private TokenRepo tokenRepo;

    public CommonResponse login(LoginRequest loginRequest) {
        List<User> listUser = userRepo.findUsersByUsernameAndStatus(
                loginRequest.getUsername(), Constants.ACTIVE);
        if (listUser == null || listUser.isEmpty()) {
            log.error("user not found: {}", loginRequest.getUsername());
            return new CommonResponse(Constants.CODE.ERROR.USER_NOT_FOUND, Constants.MESSAGE.USER_NOT_FOUND);
        }
        if (listUser.size() > 1) {
            log.error(("data error listUser.size: " + listUser.size()));
            return new CommonResponse(Constants.CODE.ERROR.INTERNAL_ERROR, Constants.MESSAGE.INTERNAL_ERROR);
        }

        User user = listUser.get(0);
        boolean isMatched = PasswordUtils.checkPassword(loginRequest.getPassword(), user.getPassword());
        if (!isMatched) {
            log.error("password not match");
            return new CommonResponse(Constants.CODE.ERROR.PASSWORD_NOT_MATCH, Constants.MESSAGE.INVALID_USER_PASS);
        }

        Token token = Token.gen(user);
        tokenRepo.save(token);
        return new CommonResponse(Constants.CODE.SUCCESS, Constants.LOGIN.SUCCESS, token);
    }

    public String validateLoginRequest(LoginRequest loginRequest) {
        if (loginRequest == null || StringUtils.isEmpty(loginRequest.getUsername())
                || StringUtils.isEmpty(loginRequest.getPassword())) {
            return Constants.LOGIN.USER_PASS_EMPTY;
        }
        return null;
    }

    @Transactional
    public String register(RegisterRequest registerRequest) {
        String message = validateRegisterRequest(registerRequest);
        if (!StringUtils.isEmpty(message)) {
            return message;
        }

        List<User> listUser = userRepo.findUsersByUsernameAndStatus(registerRequest.getUsername(), Constants.ACTIVE);
        if (listUser != null && !listUser.isEmpty()) {
            return Constants.REGISTER.USER_EXISTED;
        }


        Team team = Team.getInstance();
        teamRepo.save(team);

        List<Player> listInitPlayer = Arrays.asList(
                Player.getInstance(PlayerType.GOAL_KEEPER, team.getId()),
                Player.getInstance(PlayerType.GOAL_KEEPER, team.getId()),
                Player.getInstance(PlayerType.GOAL_KEEPER, team.getId()),
                Player.getInstance(PlayerType.DEFENDER, team.getId()),
                Player.getInstance(PlayerType.DEFENDER, team.getId()),
                Player.getInstance(PlayerType.DEFENDER, team.getId()),
                Player.getInstance(PlayerType.DEFENDER, team.getId()),
                Player.getInstance(PlayerType.DEFENDER, team.getId()),
                Player.getInstance(PlayerType.DEFENDER, team.getId()),
                Player.getInstance(PlayerType.MIDFIELDER, team.getId()),
                Player.getInstance(PlayerType.MIDFIELDER, team.getId()),
                Player.getInstance(PlayerType.MIDFIELDER, team.getId()),
                Player.getInstance(PlayerType.MIDFIELDER, team.getId()),
                Player.getInstance(PlayerType.MIDFIELDER, team.getId()),
                Player.getInstance(PlayerType.MIDFIELDER, team.getId()),
                Player.getInstance(PlayerType.ATTACKER, team.getId()),
                Player.getInstance(PlayerType.ATTACKER, team.getId()),
                Player.getInstance(PlayerType.ATTACKER, team.getId()),
                Player.getInstance(PlayerType.ATTACKER, team.getId()),
                Player.getInstance(PlayerType.ATTACKER, team.getId())
        );
        playerRepo.saveAll(listInitPlayer);

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(PasswordUtils.encryptPassword(registerRequest.getPassword()))
                .status(Constants.ACTIVE)
                .teamId(team.getId())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        userRepo.save(user);
        return null;
    }

    public String validateRegisterRequest(RegisterRequest registerRequest) {
        if (registerRequest == null || StringUtils.isEmpty(registerRequest.getUsername())
                || StringUtils.isEmpty(registerRequest.getPassword())) {
            return Constants.REGISTER.USER_PASS_EMPTY;
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return Constants.REGISTER.CONFIRM_PASSWORD_NOT_MATCH;
        }

        if (!com.example.gradlesocceronline.utils.StringUtils.isValidEmailAddress(registerRequest.getUsername())) {
            return Constants.REGISTER.INVALID_USER_NAME;
        }

        if (!PasswordUtils.validateStrongPassword(registerRequest.getPassword())) {
            return Constants.REGISTER.WEAK_PASSWORD;
        }

        return null;
    }
}
