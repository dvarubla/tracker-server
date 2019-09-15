package ru.aborisov.testtask.dao;

import ru.aborisov.testtask.resource.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepositoryAdapter {
    void save(ExpenseRecord record);
    Optional<ExpenseRecord> findById(int id);
    List<ExpenseRecord> searchByAllFields(SearchQuery query);
    List<ExpenseRecord> searchByAllFields(SearchQuery query, String login);
}
