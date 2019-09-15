package ru.aborisov.testtask.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public interface ExpenseRepository extends PagingAndSortingRepository<ExpenseRecord, Integer> {
    @Query(
            "select e from ExpenseRecord e where lower(e.description) like lower(concat('%', :query,'%')) " +
                    "or lower(e.comment) like lower(concat('%', :query,'%')) " +
                    "or cast(e.recordDate as string) like lower(concat('%', :query,'%')) " +
                    "or cast(e.cost as string) like lower(concat('%', :query,'%'))"
    )
    List<ExpenseRecord> searchByAllFields(@Param("query") String query, Pageable pageable);

    @Query(
            "select e from ExpenseRecord e where e.appUser.login = :login and ( " +
                    "lower(e.description) like lower(concat('%', :query,'%')) " +
                    "or lower(e.comment) like lower(concat('%', :query,'%')) " +
                    "or cast(e.recordDate as string) like lower(concat('%', :query,'%')) " +
                    "or cast(e.cost as string) like lower(concat('%', :query,'%')))"
    )
    List<ExpenseRecord> searchByAllFields(@Param("query") String query, @Param("login") String login, Pageable pageable);

    @Query(
            "select coalesce(sum(e.cost), 0) from ExpenseRecord e where e.recordDate between :startDate and :endDate"
    )
    BigDecimal getTotal(@Param("startDate") OffsetDateTime startDate, @Param("endDate") OffsetDateTime endDate);
    @Query(
            "select coalesce(sum(e.cost), 0) from ExpenseRecord e where e.recordDate between :startDate and :endDate and e.appUser.login = :login"
    )
    BigDecimal getTotal(@Param("startDate") OffsetDateTime start, @Param("endDate") OffsetDateTime end, @Param("login") String login);

    @Query(
            "select coalesce(avg(e.cost), 0) from ExpenseRecord e where e.recordDate between :startDate and :endDate"
    )
    BigDecimal getAverage(@Param("startDate") OffsetDateTime startDate, @Param("endDate") OffsetDateTime endDate);
    @Query(
            "select coalesce(avg(e.cost), 0) from ExpenseRecord e where e.recordDate between :startDate and :endDate and e.appUser.login = :login"
    )
    BigDecimal getAverage(@Param("startDate") OffsetDateTime start, @Param("endDate") OffsetDateTime end, @Param("login") String login);
}
