package com.myspring.myproject.user.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myspring.myproject.exception.UserEmailDuplicateException;
import com.myspring.myproject.user.model.dao.UserMapper;
import com.myspring.myproject.user.model.dto.UserDTO;
import com.myspring.myproject.user.model.vo.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserDTO user) {

        UserDTO searchedUser = mapper.getUserByUserEmail(user.getUserEmail());
        if(searchedUser != null){
            throw new UserEmailDuplicateException("이미 가입된 이메일입니다.");
        }

        User userValue = User.builder()
                             .userName(user.getUserName())
                             .userPw(passwordEncoder.encode(user.getUserPw()))
                             .userEmail(user.getUserEmail())
                             .role("COMMON_USER")
                             .build();
        mapper.signUp(userValue);
    }

    @Override
    public void changePassword() {
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

}
