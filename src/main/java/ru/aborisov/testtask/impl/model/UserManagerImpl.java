package ru.aborisov.testtask.impl.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aborisov.testtask.dao.AppUser;
import ru.aborisov.testtask.dao.RoleRepository;
import ru.aborisov.testtask.dao.UserRepositoryAdapter;
import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.exception.UserNotFoundException;
import ru.aborisov.testtask.model.UserManager;
import ru.aborisov.testtask.resource.Login;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.RoleNameId;
import ru.aborisov.testtask.resource.SearchQuery;
import ru.aborisov.testtask.resource.User;
import ru.aborisov.testtask.resource.UserPublicData;

import java.util.stream.Collectors;

@Component
public class UserManagerImpl implements UserManager {
    private UserRepositoryAdapter userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;

    @Autowired
    public UserManagerImpl(
            UserRepositoryAdapter userRepository,
            RoleRepository roleRepository,
            PasswordEncoder encoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void createUser(User userData, String roleAlias) throws UserAlreadyExistsException {
        if (userRepository.existsByLogin(userData.getCredentials().getLogin())) {
            throw new UserAlreadyExistsException(userData.getCredentials().getLogin());
        }
        AppUser appUser = new AppUser();
        appUser.setLogin(userData.getCredentials().getLogin());
        appUser.setPassword(encoder.encode(userData.getCredentials().getPassword()));
        appUser.setRole(roleRepository.findByAlias(roleAlias));
        appUser.setName(userData.getName());
        userRepository.save(appUser);
    }

    @Override
    @Transactional
    public void deleteUser(Login login) throws UserNotFoundException {
        AppUser user = userRepository.findByLogin(login.getLogin());
        if (user == null) {
            throw new UserNotFoundException(login.getLogin());
        }
        userRepository.deleteById(user.getId());
    }

    @Override
    public OutputList<UserPublicData> findPublicUserData(SearchQuery query) {
        return new OutputList<>(userRepository
                .searchByAllFields(query).stream()
                .map(user ->
                        new UserPublicData(
                                user.getLogin(), user.getName(), user.getId(),
                                new RoleNameId(user.getRole().getName(), user.getRole().getId())
                        )
                )
                .collect(Collectors.toList()));
    }
}