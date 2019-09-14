package ru.aborisov.testtask.db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class V0_0_3__createAdminUser implements SpringJdbcMigration {

    @Override
    public void migrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update(
                "insert into app_user values (nextval('user_id_seq'), 'admin', ?, 'Администратор', " +
                    "(select id from role where alias = 'admin'))",
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("12345")
        );
    }
}
