package ru.aborisov.testtask.dao;

import ru.aborisov.testtask.resource.SearchQuery;

import java.util.List;

public interface UserRepositoryAdapter {
    boolean existsByLogin(String login);
    AppUser findByLogin(String login);
    List<AppUser> searchByAllFields(SearchQuery query);

    void save(AppUser user);

    void deleteById(int id);
}
