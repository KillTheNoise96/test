package com.api.usuario.service;

import com.api.usuario.config.exception.UserException;
import com.api.usuario.config.exception.UserNotFoundException;
import com.api.usuario.config.jwt.JwtGenerator;
import com.api.usuario.model.dto.UsersDto;
import com.api.usuario.model.entity.Token;
import com.api.usuario.model.entity.Users;
import com.api.usuario.model.mapper.UsersMapper;
import com.api.usuario.repo.repo.UsersRepo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepo usersRepo;

    private final JwtGenerator jwtGenerator;

    private final UsersMapper usersMapper;

    private Token token;

    @Autowired
    public UsersServiceImpl(final UsersRepo usersRepo,
                            final JwtGenerator jwtGenerator,
                            final UsersMapper usersMapper) {
        this.usersRepo = usersRepo;
        this.jwtGenerator = jwtGenerator;
        this.usersMapper = usersMapper;
    }

    public List<Users> obtenerUsers() {
        return usersRepo.findAll();
    }

    public Users saveUser(UsersDto userDto) {

        if (!EmailValidator.getInstance().isValid(userDto.getEmail())) {
            throw new UserException("Favor ingresar un correo válido.");
        }
        if (!isValidPassword(userDto.getPassword()))
            throw new UserException("La contraseña no cumple el formato adecuado. " +
                    "(Una mayuscula, letras minúsculas, y dos numeros)");


        boolean existe = obtenerUsers().stream().anyMatch(
                x -> x.getEmail().equalsIgnoreCase(userDto.getEmail()));
        if (existe) {
            throw new UserException("El correo ingresado ya se encuentra registrado.");
        }

        Users user = usersMapper.userDtoToUser(userDto);

        if (user.getId() == null) {
            user.setCreated(new Date());
            user.setActive(true);
        } else
            user.setModified(new Date());

        if (!user.getPhones().isEmpty()) {
            user.getPhones().forEach(phone -> phone.setUsers(user));
        }
        user.setLastLogin(user.getCreated());
        token = jwtGenerator.generateToken(user);
        user.setToken(token.getToken());
        return usersRepo.save(user);
    }

    public Users obtenerLogin(UsersDto userDto) {
        if (userDto != null) {
            if (StringUtils.isBlank(userDto.getEmail()))
                throw new UserException("Correo: Requerido.");
            if (StringUtils.isBlank(userDto.getPassword()))
                throw new UserException("Contraseña: Requerida.");
            if (!isValidPassword(userDto.getPassword()))
                throw new UserException("La contraseña no cumple el formato adecuado. " +
                        "(Una mayuscula, letras minúsculas, y dos numeros)");
        } else
            throw new UserNotFoundException("Favor ingresar datos.");

        Users found = obtenerUsers().stream().filter(x ->
                        x.getEmail().equalsIgnoreCase(userDto.getEmail()) &&
                                x.getPassword().equals(userDto.getPassword())).
                findFirst().orElse(null);
        if (found == null)
            throw new UserNotFoundException("Usuario no encontrado.");

        token = jwtGenerator.generateToken(found);
        found.setToken(token.getToken());
        found.setLastLogin(new Date());
        return usersRepo.save(found);
    }

    private static boolean isValidPassword(String password) {
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
