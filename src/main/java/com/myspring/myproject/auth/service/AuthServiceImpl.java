package com.myspring.myproject.auth.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.myspring.myproject.auth.model.vo.CustomUserDetails;
import com.myspring.myproject.exception.CustomAuthenticationException;
import com.myspring.myproject.token.model.service.TokenService;
import com.myspring.myproject.user.model.dto.UserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    // private final JwtUtil jwtUtil;
    @Override
    public Map<String, String> login(UserDTO user) {

        /*
         * 1. 유효성 검증(아이디, 비밀번호 값이 입력되었는가, 영어 숫자인가, 글자수가 괜찮은가
         * 2. 아이디가 MEMEBER_ID 컬럼에 존재하는 아이디인가
         * 3. 비밀번호는 컬럼에 존재하는 암호문이 사용자가 입력한 평문으로 만들어진 것이 맞는가
         * 
         * ----------------------------------
         * 
         * 토큰 발급
         * JWT
         */
        // 사용자 인증
        Authentication authentication = null;
        try{
            authentication = 
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                user.getUserEmail(),
                user.getUserPw()));
        } catch(AuthenticationException e){
            throw new CustomAuthenticationException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
        CustomUserDetails loginUser = (CustomUserDetails)authentication.getPrincipal();

        // log.info("로그인 성공!");
        // log.info("인증에 성공한 사용자의 정보 : {}", user);


        // 토큰 발급
        // JWT 라이브러리를 이용해서 AccessToken 및 RefreshToken 발급
        // String accessToken = jwtUtil.getAccessToken(user.getUsername());
        // String refreshToken = jwtUtil.getRefreshToken(user.getUsername());
        //log.info("생성된 {}의 accessToken값 : {} \nrefreshToken값 : {}", user.getUsername(), accessToken, refreshToken);
        
        // 해시코드가 다르면 다른 객체다 O ==> 같은 값으로 해시돌리면 항상 결과가 같음 
        // 해시코드가 같으면 같은 객체다 X

        Map<String, String> loginResponse = tokenService.generateToken(loginUser.getEmail(), loginUser.getUserId());

        loginResponse.put("userEmail", loginUser.getEmail());
        loginResponse.put("userName", loginUser.getUsername());
        loginResponse.put("userId", String.valueOf(loginUser.getUserId()));
        return loginResponse;
    }

    @Override
    public CustomUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        } else {
            throw new CustomAuthenticationException("인증된 사용자 정보를 찾을 수 없습니다.");
        }
    }   
}