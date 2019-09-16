package ru.aborisov.testtask.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.aborisov.testtask.config.PrivilegeAlias;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.exception.UserNotFoundException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.model.UserManager;
import ru.aborisov.testtask.resource.Id;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.ResponseStatusBody;
import ru.aborisov.testtask.resource.RoleNameId;
import ru.aborisov.testtask.resource.SearchQuery;
import ru.aborisov.testtask.resource.User;
import ru.aborisov.testtask.resource.UserCreateData;
import ru.aborisov.testtask.resource.UserDataPrivilegies;
import ru.aborisov.testtask.resource.UserPublicData;
import ru.aborisov.testtask.resource.UserUpdateData;

import javax.validation.Valid;

@RestController
@Api("Управление пользователями")
public class UserController {
    private UserManager userManager;

    @Autowired
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @ApiOperation("Регистрация пользователя, может выполняться под любым пользователем")
    @PostMapping(
            path = "/user/register"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStatusBody createUser(
            @Valid @RequestBody User user
    ) throws UserAlreadyExistsException {
        userManager.createUser(user);
        return new ResponseStatusBody("Пользователь был создан");
    }

    @ApiOperation("Удаление пользователя")
    @DeleteMapping(
            path = "/user"
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseStatusBody deleteUser(@Valid Id id, Authentication authentication)
            throws UserNotFoundException, AppSecurityException {
        boolean canManageAdmins = getCanManageAdmins(authentication);
        userManager.deleteUser(id, canManageAdmins);
        return new ResponseStatusBody("Пользователь был удалён");
    }

    @ApiOperation("Поиск пользователей")
    @GetMapping(
            path = "/users"
    )
    @ResponseStatus(HttpStatus.OK)
    public OutputList<UserPublicData> searchUsers(@Valid SearchQuery query) {
        return userManager.findPublicUserData(query);
    }

    @ApiOperation("Создание пользователей, требуются привилегии для управления")
    @PostMapping(
            path = "/user"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Id createUser(@Valid @RequestBody UserCreateData data, Authentication authentication)
            throws AppSecurityException, UserAlreadyExistsException, ValidationException {
        boolean canManageAdmins = getCanManageAdmins(authentication);
        return new Id(userManager.createUser(data, canManageAdmins));
    }

    @ApiOperation("Получить данные о текущем пользователе")
    @GetMapping(
            path = "/user/current"
    )
    @ResponseStatus(HttpStatus.OK)
    public UserDataPrivilegies getCurrentUser(Authentication authentication) throws UserNotFoundException {
        return userManager.getUser(((UserDetails) authentication.getPrincipal()).getUsername());
    }

    @ApiOperation("Обновление пользователей, требуются привилегии для управления")
    @PutMapping(
            path = "/user"
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseStatusBody updateUser(@Valid @RequestBody UserUpdateData data, Authentication authentication)
            throws AppSecurityException, UserAlreadyExistsException, ValidationException {
        boolean canManageAdmins = getCanManageAdmins(authentication);
        userManager.updateUser(data, canManageAdmins);
        return new ResponseStatusBody("Пользователь изменён");
    }

    private boolean getCanManageAdmins(Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        return details.getAuthorities().contains(
                new SimpleGrantedAuthority(PrivilegeAlias.MANAGE_ADMINS.getAlias())
        );
    }

    @ApiOperation("Получение списка всех ролей, которые можно создавать")
    @GetMapping(
            path = "/roles"
    )
    @ResponseStatus(HttpStatus.OK)
    public OutputList<RoleNameId> getManageableRoles(Authentication authentication) {
        return userManager.getManageableRoles(getCanManageAdmins(authentication));
    }
}
