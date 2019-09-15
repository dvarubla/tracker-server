package ru.aborisov.testtask.dao;

import ru.aborisov.testtask.resource.SearchQuery;

import java.util.List;

public interface ExpenseRepositoryAdapter {
    void save(ExpenseRecord record);
    List<ExpenseRecord> searchByAllFields(SearchQuery query);
    List<ExpenseRecord> searchByAllFields(SearchQuery query, String login);
}
