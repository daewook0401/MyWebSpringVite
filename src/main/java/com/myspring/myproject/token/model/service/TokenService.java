package com.myspring.myproject.token.model.service;

import java.util.Map;

public interface TokenService {
    Map<String, String> generateToken(String email, Long userId);

    Map<String, String> refreshToken(String refreshToken);
}
