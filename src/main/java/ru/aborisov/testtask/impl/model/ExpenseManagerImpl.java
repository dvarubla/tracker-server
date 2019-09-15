package ru.aborisov.testtask.impl.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aborisov.testtask.dao.AppUser;
import ru.aborisov.testtask.dao.ExpenseRecord;
import ru.aborisov.testtask.dao.ExpenseRepositoryAdapter;
import ru.aborisov.testtask.dao.UserRepositoryAdapter;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.ExpenseNotFoundException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.model.ExpenseManager;
import ru.aborisov.testtask.resource.ExpenseCreateData;
import ru.aborisov.testtask.resource.ExpenseData;
import ru.aborisov.testtask.resource.ExpenseUpdateData;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.SearchQuery;
import ru.aborisov.testtask.resource.UserNameId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ExpenseManagerImpl implements ExpenseManager {
    private final
    UserRepositoryAdapter userRepository;
    private final
    ExpenseRepositoryAdapter expenseRepository;

    @Autowired
    public ExpenseManagerImpl(UserRepositoryAdapter userRepository, ExpenseRepositoryAdapter expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    private AppUser checkIfUserExists(int id) throws ValidationException {
        Optional<AppUser> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new ValidationException("Указан несуществующий пользователь");
        }
        return userOpt.get();
    }

    private void checkManageOther(boolean canManageOther, int userId, AppUser currentUser)
            throws AppSecurityException {
        if (!canManageOther && userId != currentUser.getId()) {
            throw new AppSecurityException("Вы не можете управлять чужими записями");
        }
    }

    private AppUser checkAndGetExpenseUser(String login, boolean canManageOther, Optional<Integer> userId)
            throws AppSecurityException, ValidationException {
        @SuppressWarnings("OptionalGetWithoutIsPresent") AppUser currentUser = userRepository.findByLogin(login).get();
        AppUser targetUser;
        if (userId.isPresent()) {
            targetUser = checkIfUserExists(userId.get());
        } else {
            targetUser = currentUser;
        }
        if (userId.isPresent()) {
            checkManageOther(canManageOther, userId.get(), currentUser);
        }
        return targetUser;
    }

    @Transactional
    @Override
    public int createExpense(ExpenseCreateData expenseCreateData, String login, boolean canManageOther)
            throws AppSecurityException, ValidationException {
        AppUser targetUser = checkAndGetExpenseUser(login, canManageOther, expenseCreateData.getUserId());
        ExpenseRecord expenseRecord = new ExpenseRecord();
        expenseRecord.setAppUser(targetUser);
        expenseRecord.setRecordDate(expenseCreateData.getRecordDate());
        expenseRecord.setComment(expenseCreateData.getComment());
        expenseRecord.setCost(expenseCreateData.getCost());
        expenseRecord.setDescription(expenseCreateData.getDescription());
        expenseRepository.save(expenseRecord);
        return expenseRecord.getId();
    }

    @Transactional
    @Override
    public void updateExpense(ExpenseUpdateData data, String login, boolean canManageOther)
            throws AppSecurityException, ValidationException, ExpenseNotFoundException {
        AppUser targetUser = checkIfUserExists(data.getUserId());
        Optional<ExpenseRecord> expenseRecordOpt = expenseRepository.findById(data.getId());
        if (!expenseRecordOpt.isPresent()) {
            throw new ExpenseNotFoundException(data.getId());
        }
        @SuppressWarnings("OptionalGetWithoutIsPresent") AppUser currentUser = userRepository.findByLogin(login).get();
        checkManageOther(canManageOther, data.getUserId(), currentUser);
        ExpenseRecord expenseRecord = expenseRecordOpt.get();
        expenseRecord.setAppUser(targetUser);
        expenseRecord.setRecordDate(data.getRecordDate());
        expenseRecord.setComment(data.getComment());
        expenseRecord.setCost(data.getCost());
        expenseRecord.setDescription(data.getDescription());
        expenseRepository.save(expenseRecord);
    }

    @Override
    @Transactional
    public OutputList<ExpenseData> findExpenseData(SearchQuery query, String login, boolean canManageOther) {
        List<ExpenseRecord> expenseList;
        if (canManageOther) {
            expenseList = expenseRepository.searchByAllFields(query);
        } else {
            expenseList = expenseRepository.searchByAllFields(query, login);
        }
        return new OutputList<>(expenseList.stream()
                .map(exp ->
                        new ExpenseData(
                                exp.getId(), exp.getRecordDate(), exp.getCost(), exp.getDescription(), exp.getComment(),
                                new UserNameId(exp.getAppUser().getName(), exp.getAppUser().getId())
                        )
                )
                .collect(Collectors.toList()));
    }
}
