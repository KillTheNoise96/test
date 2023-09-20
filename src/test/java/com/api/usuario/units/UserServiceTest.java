package com.api.usuario.units;

import com.api.usuario.model.dto.UsersDto;
import com.api.usuario.model.entity.Phones;
import com.api.usuario.model.entity.Users;
import com.api.usuario.repo.crud.UsersCrud;
import com.api.usuario.service.UsersService;
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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UsersCrud usersCrud;

    @InjectMocks
    private UsersService usersService;
    private Users users;
    private UsersDto usersDto;

    @BeforeEach
    void setup() {
        users = Users.builder().name("Marcus Phoenix").email("mark_pho@gmail.com").password("Gowrules123").build();
        Phones phones = Phones.builder().cityCode("123").countryCode("123").number("12345678").build();
        List<Phones> phonesList = new ArrayList<>();
        phonesList.add(phones);

        users.setPhones(phonesList);
        usersDto = UsersDto.builder().name(users.getName()).email(users.getEmail()).password(users.getPassword()).build();
    }

    @DisplayName("Prueba guardar un usuario.")
    @Test
    void guardar() {
        given(usersCrud.save(users)).willReturn(users);
        Users savedUser = usersService.saveUser(users);
        assertThat(savedUser).isNotNull();
    }

    @DisplayName("Obtener usuario por email y clave")
    @Test
    void obtenerUsuarioPorEmailClave() {
        Users userFound = usersService
                .obtenerLogin(usersDto);
        assertThat(userFound).isNotNull();
    }

    @DisplayName("Comprobar si existe email")
    @Test
    void existeEmail() {
        assertThat(usersService.obtenerUsers().stream().anyMatch(x ->
                x.getEmail().equalsIgnoreCase("mark_pho@gmail.com"))).isTrue();
    }
}
