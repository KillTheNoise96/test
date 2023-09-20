package com.api.usuario.service;

import com.api.usuario.model.dto.UsersDto;
import com.api.usuario.model.entity.Users;

import java.util.List;

public interface UsersService {

    List<Users> obtenerUsers();

    Users saveUser(UsersDto usersDto);

    Users obtenerLogin(UsersDto user);
}
