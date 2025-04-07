package com.myspring.myproject.auth.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myspring.myproject.auth.model.vo.CustomUserDetails;
import com.myspring.myproject.user.model.dao.UserMapper;
import com.myspring.myproject.user.model.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    //AuthenticationManager가 실질적으로 사용자의 정보를 조회하는 클래스

    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UserDTO user = mapper.getUserByUserEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("존재하지 않는 사용자 입니다.");
        }

        // 사용자가 입력한 아이디값이 테이블에 존재하긴 함

        return CustomUserDetails.builder()
                .userId(user.getUserId())
                .email(user.getUserEmail())
                .password(user.getUserPw())
                .username(user.getUserName())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
                .build();
    }

    

}

