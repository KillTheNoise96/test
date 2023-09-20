package com.api.usuario.repo.repo;

import com.api.usuario.model.entity.Phones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhonesRepo extends JpaRepository<Phones,Integer> {
}
