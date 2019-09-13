package ru.aborisov.testtask.dao;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Integer> {
    boolean existsByLogin(String login);
}
