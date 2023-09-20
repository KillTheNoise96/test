package com.api.usuario.service;

import com.api.usuario.config.exception.UserException;
import com.api.usuario.config.exception.UserNotFoundException;
import com.api.usuario.model.dto.UsersDto;
import com.api.usuario.model.entity.Users;
import com.api.usuario.repo.crud.UsersCrud;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsersService {

    private UsersCrud usersCrud;

    @Autowired
    public UsersService(UsersCrud usersCrud) {
        this.usersCrud = usersCrud;
    }

    public List<Users> obtenerUsers() {
        return (List<Users>) usersCrud.findAll();
    }

    public Users saveUser(Users user) {

        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new UserException("Favor ingresar un correo válido.");
        }
        if (!isValidPassword(user.getPassword()))
            throw new UserException("La contraseña no cumple el formato adecuado. " +
                    "(Una mayuscula, letras minúsculas, y dos numeros)");


        boolean existe = obtenerUsers().stream().anyMatch(x -> x.getEmail().equalsIgnoreCase(user.getEmail()));
        if (existe) {
            throw new UserException("El correo ingresado ya se encuentra registrado.");
        }

        if (user.getId() == null) {
            user.setCreated(new Date());
            user.setActive(true);
        } else
            user.setModified(new Date());

        if (!user.getPhones().isEmpty()) {
            user.getPhones().forEach(phone -> phone.setUsers(user));
        }
        user.setLastLogin(user.getCreated());
        return usersCrud.save(user);
    }

    public Users obtenerLogin(UsersDto user) {
        if (user != null) {
            if (StringUtils.isBlank(user.getEmail()))
                throw new UserException("Correo: Requerido.");
            if (StringUtils.isBlank(user.getPassword()))
                throw new UserException("Contraseña: Requerida.");
            if (!isValidPassword(user.getPassword()))
                throw new UserException("La contraseña no cumple el formato adecuado. " +
                        "(Una mayuscula, letras minúsculas, y dos numeros)");
        } else
            throw new UserNotFoundException("Favor ingresar datos.");

        Users found = obtenerUsers().stream().filter(x ->
                        x.getEmail().equalsIgnoreCase(user.getEmail()) && x.getPassword().equals(user.getPassword())).
                findFirst().orElse(null);
        if (found == null)
            throw new UserNotFoundException("Usuario no encontrado.");

        found.setLastLogin(new Date());
        return usersCrud.save(found);
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9][0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);

        return m.matches();
    }
}
