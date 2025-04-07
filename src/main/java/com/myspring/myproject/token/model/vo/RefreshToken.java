package com.myspring.myproject.token.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RefreshToken {
    private String token;
    private Long userId;
    private Long expiration;
}
