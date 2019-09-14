package ru.aborisov.testtask.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.aborisov.testtask.dao.AppUser;
import ru.aborisov.testtask.dao.UserRepository;
import ru.aborisov.testtask.dao.UserRepositoryAdapter;
import ru.aborisov.testtask.resource.SearchQuery;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryAdapterImpl implements UserRepositoryAdapter {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryAdapterImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public AppUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<AppUser> searchByAllFields(SearchQuery query) {
        return userRepository.searchByAllFields(
                query.getQuery(), PageRequest.of(query.getPage(), query.getCount(), Sort.by("login").ascending())
        );
    }

    @Override
    public void save(AppUser user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<AppUser> findById(int id) {
        return userRepository.findById(id);
    }
}
