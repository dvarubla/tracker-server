package ru.aborisov.testtask.model;

import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.resource.User;

public interface UserManager {
    void createUser(User userData, String roleAlias) throws UserAlreadyExistsException;
}
