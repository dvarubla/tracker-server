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
import ru.aborisov.testtask.resource.Id;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.RoleNameId;
import ru.aborisov.testtask.resource.SearchQuery;
import ru.aborisov.testtask.resource.User;
import ru.aborisov.testtask.resource.UserCreateData;
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
    public void deleteUser(Id id, boolean canManageAdmins) throws UserNotFoundException, AppSecurityException {
        if (canManageAdmins) {
            if (!userRepository.existsById(id.getId())) {
                throw new UserNotFoundException(id.getId());
            }
        } else {
            Optional<AppUser> user = userRepository.findById(id.getId());
            if (!user.isPresent()) {
                throw new UserNotFoundException(id.getId());
            }
            if (user.get().getRole().getAlias().equals(RoleAlias.ADMIN.getAlias())) {
                throw new AppSecurityException("Вы не можете управлять администраторами");
            }
        }
        userRepository.deleteById(id.getId());
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

    private Role doRoleCheckAndGet(int roleId, boolean canManageAdmins) throws ValidationException, AppSecurityException {
        Optional<Role> role = roleRepository.findById(roleId);
        if (!role.isPresent()) {
            throw new ValidationException("Указана несуществующая роль");
        }
        if (!canManageAdmins && role.get().getAlias().equals(RoleAlias.ADMIN.getAlias())) {
            throw new AppSecurityException("Вы не можете управлять администраторами");
        }
        return role.get();
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateData data, boolean canManageAdmins)
            throws ValidationException, AppSecurityException {
        Role role = doRoleCheckAndGet(data.getRoleId(), canManageAdmins);
        Optional<AppUser> userOpt = userRepository.findById(data.getId());
        if (!userOpt.isPresent()) {
            throw new ValidationException("Указан несуществующий пользователь");
        }
        AppUser user = userOpt.get();
        user.setRole(role);
        if (data.getPassword().isPresent()) {
            user.setPassword(encoder.encode(data.getPassword().get()));
        }
        user.setName(data.getName());
        user.setRole(role);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public int createUser(UserCreateData data, boolean canManageAdmins)
            throws UserAlreadyExistsException, ValidationException, AppSecurityException {
        Role role = doRoleCheckAndGet(data.getRoleId(), canManageAdmins);
        AppUser user = createUserCommon(data.getLogin(), data.getPassword(), data.getName());
        user.setRole(role);
        userRepository.save(user);
        return user.getId();
    }
}
