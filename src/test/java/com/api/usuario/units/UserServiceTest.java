package com.api.usuario.units;

import com.api.usuario.config.jwt.JwtGenerator;
import com.api.usuario.model.dto.UsersDto;
import com.api.usuario.model.entity.Phones;
import com.api.usuario.model.entity.Token;
import com.api.usuario.model.entity.Users;
import com.api.usuario.model.mapper.UsersMapper;
import com.api.usuario.repo.repo.UsersRepo;
import com.api.usuario.service.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UsersRepo usersRepo;

    @InjectMocks
    private UsersServiceImpl usersService;
    @Mock
    private UsersMapper usersMapper;
    @Mock
    private JwtGenerator jwtGenerator;
    private Users users;
    private final List<Users> usersList = new ArrayList<>();
    private UsersDto usersDto;

    private Token token;

    @BeforeEach
    void setup() {
        users = Users.builder().name("Marcus Phoenix").email("mark_pho@gmail.com").password("Gowrules123").build();
        Phones phones = Phones.builder().cityCode("123").countryCode("123").number("12345678").build();
        List<Phones> phonesList = new ArrayList<>();
        phonesList.add(phones);

        users.setPhones(phonesList);

        usersDto = UsersDto.builder().name(users.getName()).email(users.getEmail()).password(users.getPassword()).build();

        usersList.add(users);

        token = new Token("1234");
    }

    @DisplayName("Prueba guardar un usuario.")
    @Test
    void guardar() {
        given(usersRepo.save(users)).willReturn(users);
        given(usersMapper.userDtoToUser(usersDto)).willReturn(users);
        given(jwtGenerator.generateToken(users)).willReturn(token);
        Users savedUser = usersService.saveUser(usersDto);
        assertThat(savedUser).isNotNull();
    }

    @DisplayName("Obtener usuario por email y clave")
    @Test
    void obtenerUsuarioPorEmailClave() {
        given(usersRepo.findAll()).willReturn(usersList);
        given(jwtGenerator.generateToken(any(Users.class))).willReturn(token);
        given(usersRepo.save(users)).willReturn(users);
        Users userFound = usersService
                .obtenerLogin(usersDto);
        assertThat(userFound).isNotNull();
    }

    @DisplayName("Comprobar si existe email")
    @Test
    void existeEmail() {
        given(usersRepo.findAll()).willReturn(usersList);
        assertThat(usersService.obtenerUsers().stream().anyMatch(x ->
                x.getEmail().equalsIgnoreCase("mark_pho@gmail.com"))).isTrue();
    }
}
