package com.example.gradlesocceronline.filter;

import com.example.gradlesocceronline.model.Token;
import com.example.gradlesocceronline.model.response.CommonResponse;
import com.example.gradlesocceronline.service.TokenService;
import com.example.gradlesocceronline.utils.Constants;
import com.example.gradlesocceronline.utils.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.gradlesocceronline.utils.Constants.REQUEST_ATTRIBUTE.TEAM_ID;
import static com.example.gradlesocceronline.utils.Constants.REQUEST_ATTRIBUTE.USER_ID;

@Order(1)
@Component
@Slf4j
public class AuthenticationFilter implements Filter {
    @Value("${auth.not.check.api}")
    private String notCheckAPI;

    @Autowired
    private TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String rawToken = req.getHeader("token");
        log.info("rawToken: {}", rawToken);
        if (!isByPassAPI(req) && !isValidToken(rawToken)) {
            res.setHeader("Content-Type", "application/json");
            res.setStatus(HttpStatus.OK.value());
            CommonResponse authorizationError = CommonResponse.builder()
                    .code(Constants.CODE.ERROR.AUTHORIZATION_FAIL)
                    .message(Constants.MESSAGE.INVALID_TOKEN)
                    .build();
            res.getOutputStream().write(StringUtils.toJson(authorizationError).getBytes(StandardCharsets.UTF_8));
            return;
        }

        log.info("Starting a transaction for req : {}", req.getRequestURI());

        if (!isByPassAPI(req)) {
            Token token = new Token(rawToken);
            request.setAttribute(USER_ID, token.getUserId());
            request.setAttribute(TEAM_ID, token.getTeamId());
        }

        chain.doFilter(request, response);
        log.info("Committing a transaction for req : {}", req.getRequestURI());
    }


    private boolean isByPassAPI(HttpServletRequest req) {
        String url = req.getRequestURI();
        return notCheckAPI.contains(url);
    }

    private boolean isValidToken(String rawToken) {
        if (org.springframework.util.StringUtils.isEmpty(rawToken)) {
            return false;
        }

        Token token = tokenService.get(rawToken);
        return token != null && token.validate();
    }

}
