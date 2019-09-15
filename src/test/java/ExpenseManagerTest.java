import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.aborisov.testtask.dao.AppUser;
import ru.aborisov.testtask.dao.ExpenseRecord;
import ru.aborisov.testtask.dao.ExpenseRepositoryAdapter;
import ru.aborisov.testtask.dao.Role;
import ru.aborisov.testtask.dao.UserRepositoryAdapter;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.impl.model.ExpenseManagerImpl;
import ru.aborisov.testtask.model.ExpenseManager;
import ru.aborisov.testtask.resource.ExpenseCreateData;
import ru.aborisov.testtask.resource.ExpenseData;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.SearchQuery;
import ru.aborisov.testtask.resource.UserNameId;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExpenseManagerTest {
    private ExpenseManager manager;
    private UserRepositoryAdapter userRepository;
    private ExpenseRepositoryAdapter expenseRepository;

    @BeforeEach
    void doBefore() {
        userRepository = Mockito.mock(UserRepositoryAdapter.class);
        expenseRepository = Mockito.mock(ExpenseRepositoryAdapter.class);
        manager = new ExpenseManagerImpl(userRepository, expenseRepository);
    }

    @Test
    void createExpenseForSameUser() throws ValidationException, AppSecurityException {
        OffsetDateTime curDate = OffsetDateTime.now();
        ExpenseCreateData createData = new ExpenseCreateData(
                curDate, BigDecimal.valueOf(100.5), "123", "456", Optional.empty()
        );
        AppUser rootUser = new AppUser(5, "root", "12345", "root name", new Role());
        when(userRepository.findByLogin("root")).thenReturn(Optional.of(rootUser));
        ExpenseRecord resultRecord = new ExpenseRecord(
                curDate, BigDecimal.valueOf(100.5), "123", "456", rootUser
        );
        manager.createExpense(createData, "root", false);
        verify(expenseRepository).save(eq(resultRecord));
    }

    @Test
    void createExpenseForOtherUser() throws ValidationException, AppSecurityException {
        OffsetDateTime curDate = OffsetDateTime.now();
        ExpenseCreateData createData = new ExpenseCreateData(
                curDate, BigDecimal.valueOf(100.5), "123", "456", Optional.of(6)
        );
        AppUser rootUser = new AppUser(5, "root", "12345", "root name", new Role());
        AppUser targetUser = new AppUser(6, "root 5", "123456", "root name 5", new Role());
        when(userRepository.findByLogin("root")).thenReturn(Optional.of(rootUser));
        when(userRepository.findById(6)).thenReturn(Optional.of(targetUser));
        ExpenseRecord resultRecord = new ExpenseRecord(
                curDate, BigDecimal.valueOf(100.5), "123", "456", targetUser
        );
        manager.createExpense(createData, "root", true);
        verify(expenseRepository).save(eq(resultRecord));
    }

    @Test
    void createExpenseForOtherUserFail() {
        OffsetDateTime curDate = OffsetDateTime.now();
        ExpenseCreateData createData = new ExpenseCreateData(
                curDate, BigDecimal.valueOf(100.5), "123", "456", Optional.of(6)
        );
        AppUser rootUser = new AppUser(5, "root", "12345", "root name", new Role());
        AppUser targetUser = new AppUser(6, "root 5", "123456", "root name 5", new Role());
        when(userRepository.findByLogin("root")).thenReturn(Optional.of(rootUser));
        when(userRepository.findById(6)).thenReturn(Optional.of(targetUser));
        assertThrows(AppSecurityException.class, () -> manager.createExpense(createData, "root", false));
        verify(expenseRepository, never()).save(any());
    }

    @Test
    void createExpenseForOtherUserNoUserFail() {
        OffsetDateTime curDate = OffsetDateTime.now();
        ExpenseCreateData createData = new ExpenseCreateData(
                curDate, BigDecimal.valueOf(100.5), "123", "456", Optional.of(6)
        );
        AppUser rootUser = new AppUser(5, "root", "12345", "root name", new Role());
        when(userRepository.findByLogin("root")).thenReturn(Optional.of(rootUser));
        when(userRepository.findById(6)).thenReturn(Optional.empty());
        assertThrows(ValidationException.class, () -> manager.createExpense(createData, "root", true));
        verify(expenseRepository, never()).save(any());
    }

    @Test
    void searchForExpensesCurrentUser() {
        OffsetDateTime curDate = OffsetDateTime.now();
        AppUser testUser = new AppUser(
                789, "root", "54321", "Root Name",
                new Role("3", "8", new HashSet<>(), new HashSet<>(), 56)
        );
        List<ExpenseRecord> expenseRecords = Arrays.asList(
                new ExpenseRecord(
                        1, curDate, BigDecimal.valueOf(1), "123", "456",
                        testUser
                ),
                new ExpenseRecord(
                        2, curDate, BigDecimal.valueOf(5), "125", "897",
                        testUser
                )
        );
        List<ExpenseData> expenseDatas = Arrays.asList(
                new ExpenseData(
                        1, curDate, BigDecimal.valueOf(1), "123", "456",
                        new UserNameId("Root Name", 789)
                ),
                new ExpenseData(
                        2, curDate, BigDecimal.valueOf(5), "125", "897",
                        new UserNameId("Root Name", 789)
                )
        );
        SearchQuery query = new SearchQuery(1, 2, "123");
        when(expenseRepository.searchByAllFields(eq(query))).thenReturn(expenseRecords);

        assertEquals(manager.findExpenseData(query, "root", true), new OutputList<>(expenseDatas));
    }

    @Test
    void searchForExpensesOtherUser() {
        OffsetDateTime curDate = OffsetDateTime.now();
        AppUser testUser = new AppUser(
                789, "root", "54321", "Root Name",
                new Role("3", "8", new HashSet<>(), new HashSet<>(), 56)
        );
        List<ExpenseRecord> expenseRecords = Arrays.asList(
                new ExpenseRecord(
                        1, curDate, BigDecimal.valueOf(1), "123", "456",
                        testUser
                ),
                new ExpenseRecord(
                        2, curDate, BigDecimal.valueOf(5), "125", "897",
                        testUser
                )
        );
        List<ExpenseData> expenseDatas = Arrays.asList(
                new ExpenseData(
                        1, curDate, BigDecimal.valueOf(1), "123", "456",
                        new UserNameId("Root Name", 789)
                ),
                new ExpenseData(
                        2, curDate, BigDecimal.valueOf(5), "125", "897",
                        new UserNameId("Root Name", 789)
                )
        );
        SearchQuery query = new SearchQuery(1, 2, "123");
        when(expenseRepository.searchByAllFields(eq(query), eq("root"))).thenReturn(expenseRecords);

        assertEquals(manager.findExpenseData(query, "root", false), new OutputList<>(expenseDatas));
    }
}
