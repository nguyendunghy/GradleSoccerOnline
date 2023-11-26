package com.example.gradlesocceronline.repository;

import com.example.gradlesocceronline.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepo extends JpaRepository<Token,Long> {
    List<Token> findTokenByRawTokenAndStatus(String rawToken, Long status);
}
