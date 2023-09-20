package com.api.usuario.controller;

import com.api.usuario.config.jwt.JwtGeneratorImpl;
import com.api.usuario.model.dto.UsersDto;
import com.api.usuario.model.entity.Token;
import com.api.usuario.model.entity.Users;
import com.api.usuario.model.mapper.UsersMapper;
import com.api.usuario.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UsersController {

    private final UsersService usersService;
    private final JwtGeneratorImpl jwtGenerator;

    private final UsersMapper usersMapper;

    private Token token;
    private Users user;

    @Autowired
    public UsersController(UsersService usersService,
                           JwtGeneratorImpl jwtGenerator,
                           UsersMapper usersMapper) {
        this.usersService = usersService;
        this.jwtGenerator = jwtGenerator;
        this.usersMapper = usersMapper;
    }

    @PostMapping("/user")
    public ResponseEntity<?> saveUserInfo(@RequestBody @Valid UsersDto userDto) {
        user = usersMapper.userDtoToUser(userDto);
        token = jwtGenerator.generateToken(user);
        user.setToken(token.getToken());
        user = usersService.saveUser(user);
        return new ResponseEntity<>(usersMapper.userToUserDto(user), HttpStatus.CREATED);
    }

    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UsersDto userDto) {
        user = usersMapper.userDtoToUser(userDto);
        token = jwtGenerator.generateToken(user);
        userDto.setToken(token.getToken());
        userDto = usersMapper.userToUserDto(usersService.obtenerLogin(userDto));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(usersService.obtenerUsers(), HttpStatus.OK);
    }
}
