package com.api.usuario.repo.repo;

import com.api.usuario.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Integer> {
}
