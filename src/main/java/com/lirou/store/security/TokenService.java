package com.lirou.store.security;

import org.springframework.stereotype.Service;

@Service
public class TokenService {
    public String decode(String token) {
        return token.split(" ")[1];
    }
}
