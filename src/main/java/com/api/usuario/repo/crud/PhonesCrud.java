package com.api.usuario.repo.crud;

import com.api.usuario.model.entity.Phones;
import org.springframework.data.repository.CrudRepository;

public interface PhonesCrud extends CrudRepository<Phones,Integer> {
}
