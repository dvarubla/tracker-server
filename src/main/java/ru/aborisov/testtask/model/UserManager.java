package ru.aborisov.testtask.model;

import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.exception.UserNotFoundException;
import ru.aborisov.testtask.resource.Login;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.SearchQuery;
import ru.aborisov.testtask.resource.User;
import ru.aborisov.testtask.resource.UserPublicData;

public interface UserManager {
    void createUser(User userData, String roleAlias) throws UserAlreadyExistsException;
    void deleteUser(Login login) throws UserNotFoundException;
    OutputList<UserPublicData> findPublicUserData(SearchQuery query);
}
