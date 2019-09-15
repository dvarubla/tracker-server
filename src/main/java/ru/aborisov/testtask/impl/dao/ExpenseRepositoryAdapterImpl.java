package ru.aborisov.testtask.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.aborisov.testtask.dao.ExpenseRecord;
import ru.aborisov.testtask.dao.ExpenseRepository;
import ru.aborisov.testtask.dao.ExpenseRepositoryAdapter;
import ru.aborisov.testtask.resource.SearchQuery;

import java.util.List;

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
}
