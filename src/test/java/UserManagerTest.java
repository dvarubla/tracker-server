import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.aborisov.testtask.dao.AppUser;
import ru.aborisov.testtask.dao.Role;
import ru.aborisov.testtask.dao.RoleRepository;
import ru.aborisov.testtask.dao.UserRepository;
import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.exception.UserNotFoundException;
import ru.aborisov.testtask.impl.model.UserManagerImpl;
import ru.aborisov.testtask.model.UserManager;
import ru.aborisov.testtask.resource.Credentials;
import ru.aborisov.testtask.resource.Login;
import ru.aborisov.testtask.resource.User;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserManagerTest {
    private PasswordEncoder passEncoder;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserManager userManager;

    @BeforeEach
    void before() {
        passEncoder = Mockito.mock(PasswordEncoder.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        userManager = new UserManagerImpl(userRepository, roleRepository, passEncoder);
    }

    @Test
    void throwExceptionWhenUserExists() {
        when(userRepository.existsByLogin(eq("root"))).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class,
                () ->
                        userManager.createUser(
                                new User(new Credentials("root", "12345"), "Root Name")
                                , "user"
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
                , "user"
        );

        ArgumentCaptor<AppUser> userCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        assertEquals(userCaptor.getValue(), new AppUser("root", "54321", "Root Name", userRole));
    }

    @Test
    void throwExceptionWhenUserNotFoundWhenDeleting() {
        when(userRepository.existsByLogin(eq("root"))).thenReturn(false);
        assertThrows(UserNotFoundException.class,
                () ->
                        userManager.deleteUser(new Login("root"))
        );
    }

    @Test
    void deleteUser() throws UserNotFoundException {
        when(userRepository.existsByLogin(eq("root"))).thenReturn(true);
        AppUser user = new AppUser(
                789, "root", "54321", "Root Name",
                new Role("1", "2", new HashSet<>(), new HashSet<>())
        );
        when(userRepository.findByLogin(eq("root"))).thenReturn(user);

        userManager.deleteUser(new Login("root"));

        verify(userRepository, times(1)).deleteById(789);
    }
}
