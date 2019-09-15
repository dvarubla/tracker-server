import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.aborisov.testtask.config.RoleAlias;
import ru.aborisov.testtask.dao.AppUser;
import ru.aborisov.testtask.dao.Role;
import ru.aborisov.testtask.dao.RoleRepository;
import ru.aborisov.testtask.dao.UserRepositoryAdapter;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.exception.UserNotFoundException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.impl.model.UserManagerImpl;
import ru.aborisov.testtask.model.UserManager;
import ru.aborisov.testtask.resource.Credentials;
import ru.aborisov.testtask.resource.Id;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.RoleNameId;
import ru.aborisov.testtask.resource.SearchQuery;
import ru.aborisov.testtask.resource.User;
import ru.aborisov.testtask.resource.UserCreateData;
import ru.aborisov.testtask.resource.UserPublicData;
import ru.aborisov.testtask.resource.UserUpdateData;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserManagerTest {
    private PasswordEncoder passEncoder;
    private RoleRepository roleRepository;
    private UserRepositoryAdapter userRepository;
    private UserManager userManager;

    @BeforeEach
    void before() {
        passEncoder = Mockito.mock(PasswordEncoder.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        userRepository = Mockito.mock(UserRepositoryAdapter.class);
        userManager = new UserManagerImpl(userRepository, roleRepository, passEncoder);
    }

    @Test
    void throwExceptionWhenUserExists() {
        when(userRepository.existsByLogin(eq("root"))).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class,
                () ->
                        userManager.createUser(
                                new User(new Credentials("root", "12345"), "Root Name")
                        )
        );
    }

    @Test
    void createUser() throws UserAlreadyExistsException {
        when(userRepository.existsByLogin(eq("root"))).thenReturn(false);
        Role userRole = new Role("1", "2", new HashSet<>(), new HashSet<>());
        when(roleRepository.findByAlias("user")).thenReturn(userRole);
        when(passEncoder.encode("12345")).thenReturn("54321");

        userManager.createUser(
                new User(new Credentials("root", "12345"), "Root Name")
        );

        ArgumentCaptor<AppUser> userCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        assertEquals(userCaptor.getValue(), new AppUser("root", "54321", "Root Name", userRole));
    }

    @Test
    void throwExceptionWhenUserNotFoundWhenDeleting() {
        when(userRepository.existsById(eq(6))).thenReturn(false);
        assertThrows(UserNotFoundException.class,
                () ->
                        userManager.deleteUser(new Id(6), true)
        );
    }

    @Test
    void deleteUser() throws UserNotFoundException, AppSecurityException {
        when(userRepository.existsByLogin(eq("root"))).thenReturn(true);
        AppUser user = new AppUser(
                789, "root", "54321", "Root Name",
                new Role("1", "user", new HashSet<>(), new HashSet<>())
        );
        when(userRepository.findById(eq(789))).thenReturn(Optional.of(user));

        userManager.deleteUser(new Id(789), false);

        verify(userRepository, times(1)).deleteById(789);
    }

    @Test
    void deleteAdminByManagerFail() {
        when(userRepository.existsByLogin(eq("root"))).thenReturn(true);
        AppUser user = new AppUser(
                789, "root", "54321", "Root Name",
                new Role("1", "admin", new HashSet<>(), new HashSet<>())
        );
        when(userRepository.findById(eq(789))).thenReturn(Optional.of(user));

        assertThrows(AppSecurityException.class, () -> userManager.deleteUser(new Id(789), false));
    }

    @Test
    void searchUsers() {
        SearchQuery query = new SearchQuery(1, 10, "root");
        List<AppUser> users = Arrays.asList(
                new AppUser(
                        789, "root", "54321", "Root Name",
                        new Role("3", "8", new HashSet<>(), new HashSet<>(), 56)
                ),
                new AppUser(
                        456, "root2", "54321", "Root Name2",
                        new Role("5", "9", new HashSet<>(), new HashSet<>(), 14)
                )
        );
        OutputList<UserPublicData> output = new OutputList<>(
                Arrays.asList(
                        new UserPublicData("root", "Root Name", 789, new RoleNameId("3", 56)),
                        new UserPublicData("root2", "Root Name2", 456, new RoleNameId("5", 14))
                )
        );
        when(userRepository.searchByAllFields(eq(query))).thenReturn(users);

        assertEquals(userManager.findPublicUserData(query), output);
    }

    @Test
    void createAdminByAdmin() throws AppSecurityException, UserAlreadyExistsException, ValidationException {
        UserCreateData createData = new UserCreateData("admin2", "Администратор", 1, "12345");
        Role adminRole = new Role("1", RoleAlias.ADMIN.getAlias(), new HashSet<>(), new HashSet<>());
        when(userRepository.existsByLogin("admin2")).thenReturn(false);
        when(roleRepository.findById(1)).thenReturn(Optional.of(adminRole));
        when(passEncoder.encode("12345")).thenReturn("54321");

        userManager.createUser(createData, true);

        verify(userRepository, times(1)).save(eq(
                new AppUser("admin2", "54321", "Администратор", adminRole)
        ));
    }

    @Test
    void updateManagerByManager() throws AppSecurityException, UserAlreadyExistsException, ValidationException {
        UserUpdateData updateData = new UserUpdateData("Менеджер", 5, 1, Optional.empty());
        Role managerRole = new Role("1", RoleAlias.MANAGER.getAlias(), new HashSet<>(), new HashSet<>());
        Role managerRoleOld = new Role("2", RoleAlias.MANAGER.getAlias(), new HashSet<>(), new HashSet<>());
        when(roleRepository.findById(1)).thenReturn(Optional.of(managerRole));
        when(userRepository.findById(5)).thenReturn(Optional.of(new AppUser("12", "134", "34", managerRoleOld)));

        userManager.updateUser(updateData, false);

        verify(userRepository, times(1)).save(eq(
                new AppUser("12", "134", "Менеджер", managerRole)
        ));
    }

    @Test
    void updateUserNoUserFail() {
        UserUpdateData updateData = new UserUpdateData("Менеджер", 5, 1, Optional.empty());
        Role managerRole = new Role("1", RoleAlias.MANAGER.getAlias(), new HashSet<>(), new HashSet<>());
        when(roleRepository.findById(1)).thenReturn(Optional.of(managerRole));
        when(userRepository.findById(5)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> userManager.updateUser(updateData, false));

        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUserNoRoleFail() {
        UserUpdateData updateData = new UserUpdateData("Менеджер", 5, 1, Optional.empty());
        when(userRepository.existsByLogin("manager")).thenReturn(false);
        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> userManager.updateUser(updateData, false));

        verify(userRepository, never()).save(any());
    }

    @Test
    void createAdminByManagerFail() {
        UserCreateData createData = new UserCreateData("admin2", "Администратор", 1, "12345");
        Role adminRole = new Role("1", RoleAlias.ADMIN.getAlias(), new HashSet<>(), new HashSet<>());
        when(userRepository.existsByLogin("admin2")).thenReturn(false);
        when(roleRepository.findById(1)).thenReturn(Optional.of(adminRole));
        when(passEncoder.encode("12345")).thenReturn("54321");

        assertThrows(AppSecurityException.class, () -> userManager.createUser(createData, false));

        verify(userRepository, never()).save(any());
    }
}
