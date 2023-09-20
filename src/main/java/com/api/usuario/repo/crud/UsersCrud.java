package com.api.usuario.repo.crud;

import com.api.usuario.model.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersCrud extends CrudRepository<Users, Integer> {
}
