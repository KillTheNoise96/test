package com.api.usuario.controller;

import com.api.usuario.model.dto.UsersDto;
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
    private final UsersMapper usersMapper;

    @Autowired
    public UsersController(final UsersService usersService,
                           final UsersMapper usersMapper) {
        this.usersService = usersService;
        this.usersMapper = usersMapper;
    }

    @PostMapping("/user")
    public ResponseEntity<?> saveUserInfo(@RequestBody @Valid UsersDto userDto) {
        userDto = usersMapper.userToUserDto(usersService.saveUser(userDto));
        return new ResponseEntity<>(userDto,
                HttpStatus.CREATED);
    }

    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UsersDto userDto) {
        userDto = usersMapper.userToUserDto(usersService.obtenerLogin(userDto));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(usersService.obtenerUsers(), HttpStatus.OK);
    }
}
