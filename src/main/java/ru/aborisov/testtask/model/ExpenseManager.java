package ru.aborisov.testtask.model;

import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.resource.ExpenseCreateData;

public interface ExpenseManager {
    int createExpense(ExpenseCreateData expenseCreateData, String login, boolean canManageOther) throws AppSecurityException, ValidationException;
}
