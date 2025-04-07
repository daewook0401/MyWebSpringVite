package com.myspring.myproject.user.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class User {
    private Long userId;
    private String userName;
    private String userPw;
    private String userEmail;
    private String role;
    private String adminAcc;
    private Date createdAt;
    private Date updatedAt;
}
