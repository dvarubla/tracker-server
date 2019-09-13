package ru.aborisov.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.model.UserManager;
import ru.aborisov.testtask.resource.ResponseStatusBody;
import ru.aborisov.testtask.resource.User;

@RestController
public class UserController {
    private UserManager userManager;

    @Autowired
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PostMapping(
            path = "/user"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStatusBody createUser(
            @RequestBody User user
    ) throws UserAlreadyExistsException {
        userManager.createUser(user, "user");
        return new ResponseStatusBody("Пользователь был создан");
    }
}
