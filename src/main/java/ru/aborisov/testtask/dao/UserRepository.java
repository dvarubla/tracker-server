package ru.aborisov.testtask.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<AppUser, Integer> {
    boolean existsByLogin(String login);
    boolean existsById(int id);
    Optional<AppUser> findByLogin(String login);

    @Query("select u from AppUser u where lower(u.login) like %:query% or lower(name) like %:query%")
    List<AppUser> searchByAllFields(@Param("query") String query, Pageable pageable);
}
