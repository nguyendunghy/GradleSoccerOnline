package com.example.gradlesocceronline.service;

import com.example.gradlesocceronline.model.Token;
import com.example.gradlesocceronline.repository.TokenRepo;
import com.example.gradlesocceronline.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TokenService {
    @Autowired
    private TokenRepo tokenRepo;

    public Token get(String raw){
        List<Token> list = tokenRepo.findTokenByRawTokenAndStatus(raw, Constants.ACTIVE);
        if(list == null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    public void save(Token token){
        tokenRepo.save(token);
    }

    public String logout(String rawToken) {
        List<Token> tokenList = tokenRepo.findTokenByRawTokenAndStatus(rawToken, Constants.ACTIVE);
        if(tokenList == null || tokenList.isEmpty()){
            return Constants.MESSAGE.TOKEN_NOT_FOUND;
        }

        for(Token token : tokenList){
            token.setStatus(Constants.IN_ACTIVE);
            token.setUpdatedAt(new Date());
        }

        tokenRepo.saveAll(tokenList);
        return null;
    }
}
