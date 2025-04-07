package com.myspring.myproject.user.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private Long userId;
    private String userName;

    @Size(min = 5, max = 15, message = "비밀번호 값은 5글자 이상 15글자 이하만 사용할 수 있습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "비밀번호 값은 영어/숫자만 사용할 수 있습니다.")
    @NotBlank(message = "비밀번호 값은 비어있을 수 없습니다.")
    private String userPw;

    @Email(message="올바른 이메일 형식으로 입력해야합니다.")
    @NotBlank(message = "이메일을 입력해야합니다.")
    private String userEmail;
    private String role;
    private String adminAcc;
    private Date createdAt;
    private Date updatedAt;
}
