package ru.aborisov.testtask.model;

import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.ExpenseNotFoundException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.resource.DatePeriod;
import ru.aborisov.testtask.resource.ExpenseCreateData;
import ru.aborisov.testtask.resource.ExpenseData;
import ru.aborisov.testtask.resource.ExpenseUpdateData;
import ru.aborisov.testtask.resource.Id;
import ru.aborisov.testtask.resource.MoneyValue;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.SearchQuery;

public interface ExpenseManager {
    int createExpense(ExpenseCreateData expenseCreateData, String login, boolean canManageOther) throws AppSecurityException, ValidationException;

    OutputList<ExpenseData> findExpenseData(SearchQuery query, String login, boolean canManageOther);

    void updateExpense(ExpenseUpdateData data, String login, boolean canManageOther) throws AppSecurityException, ValidationException, ExpenseNotFoundException;

    void deleteExpense(Id id, String login, boolean canManageOther) throws AppSecurityException, ExpenseNotFoundException;

    MoneyValue getAverageByPeriod(DatePeriod period, String login, boolean canManageOther);

    MoneyValue getTotalByPeriod(DatePeriod period, String login, boolean canManageOther);
}
