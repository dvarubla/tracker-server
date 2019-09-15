package ru.aborisov.testtask.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends PagingAndSortingRepository<ExpenseRecord, Integer> {
    @Query(
            "select e from ExpenseRecord e where lower(e.description) like %:query% or lower(e.comment) like %:query% " +
            "or cast(e.recordDate as string) like %:query% or cast(e.cost as string) like %:query%"
    )
    List<ExpenseRecord> searchByAllFields(@Param("query") String query, Pageable pageable);

    @Query(
            "select e from ExpenseRecord e where e.appUser.login = :login and ( " +
            "lower(e.description) like %:query% or lower(e.comment) like %:query% " +
            "or cast(e.recordDate as string) like %:query% or cast(e.cost as string) like %:query%)"
    )
    List<ExpenseRecord> searchByAllFields(@Param("query") String query, @Param("login") String login, Pageable pageable);
}
