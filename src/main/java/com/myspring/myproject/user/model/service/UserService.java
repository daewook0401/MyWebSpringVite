package com.myspring.myproject.user.model.service;

import com.myspring.myproject.user.model.dto.UserDTO;

public interface UserService {
    
    // 회원가입
    void signUp(UserDTO user);
    
    // 비밀번호 변경
    void changePassword();

    // 계정 삭제
}
