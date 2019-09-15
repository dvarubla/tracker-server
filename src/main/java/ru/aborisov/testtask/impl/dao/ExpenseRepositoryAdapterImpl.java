package ru.aborisov.testtask.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aborisov.testtask.dao.ExpenseRecord;
import ru.aborisov.testtask.dao.ExpenseRepository;
import ru.aborisov.testtask.dao.ExpenseRepositoryAdapter;

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
}
