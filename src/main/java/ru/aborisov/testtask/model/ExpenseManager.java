package ru.aborisov.testtask.model;

import org.springframework.transaction.annotation.Transactional;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.resource.ExpenseCreateData;
import ru.aborisov.testtask.resource.ExpenseData;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.SearchQuery;

public interface ExpenseManager {
    int createExpense(ExpenseCreateData expenseCreateData, String login, boolean canManageOther) throws AppSecurityException, ValidationException;

    @Transactional
    OutputList<ExpenseData> findExpenseData(SearchQuery query, String login, boolean canManageOther);
}
