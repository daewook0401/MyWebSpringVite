package com.myspring.myproject.auth.service;

import java.util.Map;

import com.myspring.myproject.auth.model.vo.CustomUserDetails;
import com.myspring.myproject.user.model.dto.UserDTO;

public interface AuthService {
    Map<String, String> login(UserDTO user);
    
    CustomUserDetails getUserDetails();
}
