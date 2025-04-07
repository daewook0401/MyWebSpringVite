package com.myspring.myproject.token.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.myspring.myproject.auth.util.JwtUtil;
import com.myspring.myproject.token.model.dao.TokenMapper;
import com.myspring.myproject.token.model.vo.RefreshToken;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final JwtUtil tokenUtil;
    private final TokenMapper tokenMapper;

    @Override
    public Map<String, String> generateToken(String email, Long userId) {

        Map<String, String> tokens = createToken(email);
        
        saveToken(tokens.get("refreshToken"), userId);
        
        tokenMapper.deleteExpiredRefreshToken(System.currentTimeMillis());

        return tokens;
    }

    private Map<String, String> createToken(String email){
        String accessToken = tokenUtil.getAccessToken(email);
        String refreshToken = tokenUtil.getRefreshToken(email);

        Map<String, String> tokens = new HashMap();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    private void saveToken(String refreshToken, Long userId){
        RefreshToken token = RefreshToken.builder().token(refreshToken).userId(userId).expiration(System.currentTimeMillis()+ 36000000L * 72).build();

        // 인서트
        tokenMapper.saveToken(token);
    }

    @Override
    public Map<String, String> refreshToken(String refreshToken){
        RefreshToken token = RefreshToken.builder().token(refreshToken).build();
        RefreshToken responseToken = tokenMapper.findByToken(token);
        
        if(responseToken == null || token.getExpiration() < System.currentTimeMillis()){
            throw new RuntimeException("유효하지 않은 토큰입니다.");   
        }
        String email = getEmailByToken(refreshToken);
        Long userId = responseToken.getUserId();
        return generateToken(email, userId);
    }

    private String getEmailByToken(String refreshToken){
        Claims claims = tokenUtil.parseJwt(refreshToken);
        return claims.getSubject();
    }
}