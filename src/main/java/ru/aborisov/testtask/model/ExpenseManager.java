package ru.aborisov.testtask.model;

import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.ExpenseNotFoundException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.resource.ExpenseCreateData;
import ru.aborisov.testtask.resource.ExpenseData;
import ru.aborisov.testtask.resource.ExpenseUpdateData;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.SearchQuery;

public interface ExpenseManager {
    int createExpense(ExpenseCreateData expenseCreateData, String login, boolean canManageOther) throws AppSecurityException, ValidationException;

    OutputList<ExpenseData> findExpenseData(SearchQuery query, String login, boolean canManageOther);

    void updateExpense(ExpenseUpdateData data, String login, boolean canManageOther) throws AppSecurityException, ValidationException, ExpenseNotFoundException;
}
