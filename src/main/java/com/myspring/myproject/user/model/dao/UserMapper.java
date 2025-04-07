package com.myspring.myproject.user.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.myspring.myproject.user.model.dto.UserDTO;
import com.myspring.myproject.user.model.vo.User;

@Mapper
public interface UserMapper {

    int signUp(User user);

    UserDTO getUserByUserEmail(String userEmail);

}
