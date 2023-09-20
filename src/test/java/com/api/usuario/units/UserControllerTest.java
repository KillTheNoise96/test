package com.api.usuario.units;

import com.api.usuario.config.jwt.JwtGeneratorImpl;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private JwtGeneratorImpl jwtGenerator;

    @MockBean
    private UsersMapper userMapper;

    @DisplayName("Guardar un usuario.")
    @Test
    void guardar() throws Exception {


        Users user = Users.builder()
                .name("Marcus Phoenix")
                .email("mark_pho@gmail.com")
                .password("Gowrules123")
                .build();
        Token token = new Token("1234");
        UsersDto userDto = UsersDto.builder()
                .name("Marcus Phoenix")
                .email("mark_pho@gmail.com")
                .password("Gowrules123")
                .token(token.getToken())
                .build();

        given(userMapper.userDtoToUser(any(UsersDto.class))).willReturn(user);

        given(userService.saveUser(any(Users.class))).willReturn(user);

        given(jwtGenerator.generateToken(any(Users.class))).willReturn(token);

        given(userMapper.userToUserDto(any(Users.class))).willReturn(userDto);

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

        Users user = Users.builder()
                .email("mark_pho@gmail.com")
                .password("Gowrules123")
                .build();
        Token token = new Token("1234");
        UsersDto userDto = UsersDto.builder()
                .name("Marcus Phoenix")
                .email("mark_pho@gmail.com")
                .password("Gowrules123")
                .token(token.getToken())
                .build();

        given(userMapper.userDtoToUser(any(UsersDto.class))).willReturn(user);

        given(jwtGenerator.generateToken(any(Users.class))).willReturn(token);

        given(userService.obtenerLogin(userDto)).willReturn(user);

        given(userMapper.userToUserDto(any(Users.class))).willReturn(userDto);

        ResultActions resultActions = mockMvc.perform(post("/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDto)));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(userDto.getToken())));

    }
}
