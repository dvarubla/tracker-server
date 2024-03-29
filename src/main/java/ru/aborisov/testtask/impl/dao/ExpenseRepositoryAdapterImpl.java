package ru.aborisov.testtask.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.aborisov.testtask.dao.ExpenseRecord;
import ru.aborisov.testtask.dao.ExpenseRepository;
import ru.aborisov.testtask.dao.ExpenseRepositoryAdapter;
import ru.aborisov.testtask.resource.SearchQuery;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ExpenseRepositoryAdapterImpl implements ExpenseRepositoryAdapter {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseRepositoryAdapterImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void save(ExpenseRecord record) {
        expenseRepository.save(record);
    }

    @Override
    public Optional<ExpenseRecord> findById(int id) {
        return expenseRepository.findById(id);
    }

    @Override
    public List<ExpenseRecord> searchByAllFields(SearchQuery query) {
        return expenseRepository.searchByAllFields(
                query.getQuery(), PageRequest.of(query.getPage(), query.getCount(), Sort.by("recordDate").ascending())
        );
    }

    @Override
    public List<ExpenseRecord> searchByAllFields(SearchQuery query, String login) {
        return expenseRepository.searchByAllFields(
                query.getQuery(), login, PageRequest.of(query.getPage(), query.getCount(), Sort.by("recordDate").ascending())
        );
    }

    @Override
    public void deleteById(int id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotal(OffsetDateTime start, OffsetDateTime end, String login) {
        return expenseRepository.getTotal(start, end, login);
    }

    @Override
    public BigDecimal getAverage(OffsetDateTime startDate, OffsetDateTime endDate, String login) {
        return expenseRepository.getAverage(startDate, endDate, login);
    }

    @Override
    public BigDecimal getTotal(OffsetDateTime start, OffsetDateTime end) {
        return expenseRepository.getTotal(start, end);
    }

    @Override
    public BigDecimal getAverage(OffsetDateTime startDate, OffsetDateTime endDate) {
        return expenseRepository.getAverage(startDate, endDate);
    }
}
