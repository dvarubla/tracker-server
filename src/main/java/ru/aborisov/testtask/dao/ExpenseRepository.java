package ru.aborisov.testtask.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExpenseRepository extends PagingAndSortingRepository<ExpenseRecord, Integer> {
}
