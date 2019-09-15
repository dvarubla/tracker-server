package ru.aborisov.testtask.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByAlias(String alias);
    List<Role> findAll();
}
