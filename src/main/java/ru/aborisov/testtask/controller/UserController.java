package ru.aborisov.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import ru.aborisov.testtask.resource.SearchQuery;
import ru.aborisov.testtask.resource.User;
import ru.aborisov.testtask.resource.UserPublicData;
import ru.aborisov.testtask.resource.UserUpdateData;

@RestController
public class UserController {
    private UserManager userManager;

    @Autowired
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PostMapping(
            path = "/users"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStatusBody createUser(
            @RequestBody User user
    ) throws UserAlreadyExistsException {
        userManager.createUser(user);
        return new ResponseStatusBody("Пользователь был создан");
    }

    @DeleteMapping(
            path = "/user"
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseStatusBody deleteUser(@RequestBody Id id, Authentication authentication)
            throws UserNotFoundException, AppSecurityException {
        boolean canManageAdmins = getCanManageAdmins(authentication);
        userManager.deleteUser(id, canManageAdmins);
        return new ResponseStatusBody("Пользователь был удалён");
    }

    @GetMapping(
            path = "/users"
    )
    @ResponseStatus(HttpStatus.OK)
    public OutputList<UserPublicData> searchUsers(SearchQuery query) {
        return userManager.findPublicUserData(query);
    }

    @PostMapping(
            path = "/allusers"
    )
    public ResponseEntity<ResponseStatusBody> createOrUpdateUser(@RequestBody UserUpdateData data, Authentication authentication)
            throws AppSecurityException, UserAlreadyExistsException, ValidationException {
        boolean canManageAdmins = getCanManageAdmins(authentication);
        boolean isNew = userManager.createOrUpdateUser(data, canManageAdmins);
        return new ResponseEntity<>(
                new ResponseStatusBody(isNew ? "Пользователь создан" : "Пользователь изменён"),
                isNew ? HttpStatus.CREATED : HttpStatus.OK
        );
    }

    private boolean getCanManageAdmins(Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        return details.getAuthorities().contains(
                new SimpleGrantedAuthority(PrivilegeAlias.MANAGE_ADMINS.getAlias())
        );
    }
}
