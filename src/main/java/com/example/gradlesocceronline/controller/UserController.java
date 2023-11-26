package com.example.gradlesocceronline.controller;

import com.example.gradlesocceronline.model.request.LoginRequest;
import com.example.gradlesocceronline.model.request.RegisterRequest;
import com.example.gradlesocceronline.model.response.CommonResponse;
import com.example.gradlesocceronline.service.TokenService;
import com.example.gradlesocceronline.service.UserService;
import com.example.gradlesocceronline.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.gradlesocceronline.utils.Constants.INTERNAL_ERROR;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;


    @PostMapping(value = "/auth", consumes = "application/json", produces = "application/json")
    public CommonResponse authentication(@RequestBody LoginRequest loginRequest) {
        log.info("Start authentication");
        String errMessage = userService.validateLoginRequest(loginRequest);
        if (StringUtils.isNotEmpty(errMessage)) {
            return new CommonResponse(INTERNAL_ERROR, errMessage, null);
        }

        CommonResponse commonResponse = userService.login(loginRequest);
        return commonResponse;
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public CommonResponse register(@RequestBody RegisterRequest registerRequest) {
        log.info("start register");
        String errMessage = userService.register(registerRequest);
        if (StringUtils.isNotEmpty(errMessage)) {
            return new CommonResponse(INTERNAL_ERROR, errMessage);
        }

        return new CommonResponse(Constants.CODE.SUCCESS, Constants.LOGIN.SUCCESS);
    }

    @PostMapping(value = "/logout", consumes = "application/json", produces = "application/json")
    public CommonResponse logout(@RequestHeader("token") String rawToken) {
        log.info("start logout");
        String errMsg = tokenService.logout(rawToken);
        if (StringUtils.isNotEmpty(errMsg)) {
            return new CommonResponse(Constants.CODE.ERROR.LOGOUT, errMsg);
        }

        return new CommonResponse(Constants.CODE.SUCCESS, Constants.LOGIN.SUCCESS);
    }
}
