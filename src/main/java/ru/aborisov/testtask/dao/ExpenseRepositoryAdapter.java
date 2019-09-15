package ru.aborisov.testtask.dao;

import ru.aborisov.testtask.resource.SearchQuery;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepositoryAdapter {
    void save(ExpenseRecord record);
    Optional<ExpenseRecord> findById(int id);
    void deleteById(int id);
    List<ExpenseRecord> searchByAllFields(SearchQuery query);
    List<ExpenseRecord> searchByAllFields(SearchQuery query, String login);
    BigDecimal getTotal(OffsetDateTime start, OffsetDateTime end, String login);
    BigDecimal getAverage(OffsetDateTime startDate, OffsetDateTime endDate, String login);
    BigDecimal getTotal(OffsetDateTime start, OffsetDateTime end);
    BigDecimal getAverage(OffsetDateTime startDate, OffsetDateTime endDate);

}
