package com.api.usuario.units;

import com.api.usuario.model.entity.Users;
import com.api.usuario.repo.crud.UsersCrud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepoTest {

    private final UsersCrud usersCrud;
    private Users user;

    @Autowired
    public UserRepoTest(UsersCrud usersCrud) {
        this.usersCrud = usersCrud;
    }

    @BeforeEach
    void setup() {
        user = Users.builder().name("Marcus Phoenix").
                email("mark_phon@gmail.com").
                password("Gowrules123").build();
    }

    @DisplayName("Guardar usuario")
    @Test
    void guardarUsuario() {
        Users userSaved = usersCrud.save(user);
        assertThat(userSaved).isNotNull();
    }
}
