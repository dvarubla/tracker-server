package ru.aborisov.testtask.impl.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aborisov.testtask.config.RoleAlias;
import ru.aborisov.testtask.dao.AppUser;
import ru.aborisov.testtask.dao.Role;
import ru.aborisov.testtask.dao.RoleRepository;
import ru.aborisov.testtask.dao.UserRepositoryAdapter;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.exception.UserNotFoundException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.model.UserManager;
import ru.aborisov.testtask.resource.Login;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.RoleNameId;
import ru.aborisov.testtask.resource.SearchQuery;
import ru.aborisov.testtask.resource.User;
import ru.aborisov.testtask.resource.UserPublicData;
import ru.aborisov.testtask.resource.UserUpdateData;

import java.util.Optional;
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

    private AppUser createUserCommon(String login, String password, String name) throws UserAlreadyExistsException {
        if (userRepository.existsByLogin(login)) {
            throw new UserAlreadyExistsException(login);
        }
        AppUser appUser = new AppUser();
        appUser.setLogin(login);
        appUser.setPassword(encoder.encode(password));
        appUser.setName(name);
        return appUser;
    }

    @Override
    @Transactional
    public void createUser(User userData) throws UserAlreadyExistsException {
        AppUser user = createUserCommon(
                userData.getCredentials().getLogin(), userData.getCredentials().getPassword(), userData.getName()
        );
        user.setRole(roleRepository.findByAlias(RoleAlias.USER.getAlias()));
        userRepository.save(user);
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
    @Transactional
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

    @Override
    @Transactional
    public boolean createOrUpdateUser(UserUpdateData data, boolean canManageAdmins)
            throws UserAlreadyExistsException, ValidationException, AppSecurityException {
        Optional<Role> role = roleRepository.findById(data.getRoleId());
        if (!role.isPresent()) {
            throw new ValidationException("Указана несуществующая роль");
        }
        if (!canManageAdmins && role.get().getAlias().equals(RoleAlias.ADMIN.getAlias())) {
            throw new AppSecurityException("Вы не можете управлять администраторами");
        }
        AppUser user;
        if (data.getId().isPresent()) {
            Optional<AppUser> userOpt = userRepository.findById(data.getId().get());
            if (!userOpt.isPresent()) {
                throw new ValidationException("Указан несуществующий пользователь");
            }
            user = userOpt.get();
            user.setRole(role.get());
            user.setLogin(data.getLogin());
            if (data.getPassword().isPresent()) {
                user.setPassword(encoder.encode(data.getPassword().get()));
            }
            user.setName(data.getName());
        } else {
            if (!data.getPassword().isPresent()) {
                throw new ValidationException("Нужно указать пароль");
            }
            user = createUserCommon(data.getLogin(), data.getPassword().get(), data.getName());
        }
        user.setRole(role.get());
        userRepository.save(user);
        return !data.getId().isPresent();
    }
}
