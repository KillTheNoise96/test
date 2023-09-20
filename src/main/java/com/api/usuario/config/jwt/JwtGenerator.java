package com.api.usuario.config.jwt;

import com.api.usuario.model.entity.Token;
import com.api.usuario.model.entity.Users;

public interface JwtGenerator {
    Token generateToken(Users users);
}
