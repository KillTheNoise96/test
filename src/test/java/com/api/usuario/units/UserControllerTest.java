package com.api.usuario.units;

import com.api.usuario.config.jwt.JwtGenerator;
import com.api.usuario.model.dto.UsersDto;
import com.api.usuario.model.entity.Token;
import com.api.usuario.model.entity.Users;
import com.api.usuario.model.mapper.UsersMapper;
import com.api.usuario.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersService userService;

    @MockBean
    private UsersMapper userMapper;

    @DisplayName("Guardar un usuario.")
    @Test
    void guardar() throws Exception {

        Token token = new Token("1234");
        UsersDto userDto = UsersDto.builder()
                .name("Marcus Phoenix")
                .email("mark_pho@gmail.com")
                .password("Gowrules123")
                .token(token.getToken())
                .build();

        given(userMapper.userToUserDto(userService.saveUser(any(UsersDto.class)))).willReturn(userDto);

        ResultActions resultActions = mockMvc.perform(post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token", is(userDto.getToken())));

    }

    @DisplayName("Login un usuario.")
    @Test
    void login() throws Exception {

        Token token = new Token("1234");
        UsersDto userDto = UsersDto.builder()
                .name("Marcus Phoenix")
                .email("mark_pho@gmail.com")
                .password("Gowrules123")
                .token(token.getToken())
                .build();

        given(userMapper.userToUserDto(userService.obtenerLogin(any(UsersDto.class)))).willReturn(userDto);

        ResultActions resultActions = mockMvc.perform(put("/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDto)));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(userDto.getToken())));

    }

    @DisplayName("Listar usuarios.")
    @Test
    void cargarUsuarios() throws Exception {

        List<Users> usersList = new ArrayList<>();

        given(userService.obtenerUsers()).willReturn(usersList);

        ResultActions resultActions = mockMvc.perform(get("/v1/all")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(print())
                .andExpect(status().isOk());

    }
}
