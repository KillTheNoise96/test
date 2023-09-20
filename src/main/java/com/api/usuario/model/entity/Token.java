package com.api.usuario.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private String token;

    public Token(String token) {
        this.token = token;
    }
}
