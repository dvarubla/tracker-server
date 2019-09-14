package ru.aborisov.testtask.model;

import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.exception.UserNotFoundException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.resource.Login;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.SearchQuery;
import ru.aborisov.testtask.resource.User;
import ru.aborisov.testtask.resource.UserPublicData;
import ru.aborisov.testtask.resource.UserUpdateData;

public interface UserManager {
    void createUser(User userData) throws UserAlreadyExistsException;
    void deleteUser(Login login) throws UserNotFoundException;
    OutputList<UserPublicData> findPublicUserData(SearchQuery query);
    boolean createOrUpdateUser(UserUpdateData data, boolean canManageAdmins) throws UserAlreadyExistsException, ValidationException, AppSecurityException;
}
