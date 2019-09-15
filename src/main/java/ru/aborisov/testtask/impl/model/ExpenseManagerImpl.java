package ru.aborisov.testtask.impl.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aborisov.testtask.dao.AppUser;
import ru.aborisov.testtask.dao.ExpenseRecord;
import ru.aborisov.testtask.dao.ExpenseRepositoryAdapter;
import ru.aborisov.testtask.dao.UserRepositoryAdapter;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.model.ExpenseManager;
import ru.aborisov.testtask.resource.ExpenseCreateData;

import java.util.Optional;

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

    @Transactional
    @Override
    public int createExpense(ExpenseCreateData expenseCreateData, String login, boolean canManageOther)
            throws AppSecurityException, ValidationException {
        @SuppressWarnings("OptionalGetWithoutIsPresent") AppUser currentUser = userRepository.findByLogin(login).get();
        AppUser targetUser;
        if (expenseCreateData.getUserId().isPresent()) {
             Optional<AppUser> userOpt = userRepository.findById(expenseCreateData.getUserId().get());
             if (!userOpt.isPresent()) {
                 throw new ValidationException("Указан несуществующий пользователь");
             }
             targetUser = userOpt.get();
        } else {
            targetUser = currentUser;
        }
        if (!canManageOther && expenseCreateData.getUserId().isPresent() && expenseCreateData.getUserId().get() != currentUser.getId()) {
            throw new AppSecurityException("Вы не можете управлять чужими записями");
        }
        ExpenseRecord expenseRecord = new ExpenseRecord();
        expenseRecord.setAppUser(targetUser);
        expenseRecord.setRecordDate(expenseCreateData.getRecordDate());
        expenseRecord.setComment(expenseCreateData.getComment());
        expenseRecord.setCost(expenseCreateData.getCost());
        expenseRecord.setDescription(expenseCreateData.getDescription());
        expenseRepository.save(expenseRecord);
        return expenseRecord.getId();
    }
}
