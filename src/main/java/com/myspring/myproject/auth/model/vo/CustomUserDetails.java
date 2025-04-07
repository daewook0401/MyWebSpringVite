package com.myspring.myproject.auth.model.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@Getter
@ToString
public class CustomUserDetails implements UserDetails{
    private final Long   userId;      // PRIVATE_USER.USER_ID
    private final String username;    // PRIVATE_USER.USER_NAME
    private final String password;    // PRIVATE_USER.USER_PW
    private final String email;       // PRIVATE_USER.USER_EMAIL
    private final String role;        // PRIVATE_USER.ROLE
    private final String adminAcc;    // PRIVATE_USER.ADMIN_ACC ('Y' or 'N')
    private final Collection<? extends GrantedAuthority> authorities;
}
