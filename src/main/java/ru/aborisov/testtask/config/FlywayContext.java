package ru.aborisov.testtask.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component("flyway")
public class FlywayContext {

    private DataSource dataSource;
    private boolean isDebug;

    @Autowired
    public FlywayContext(DataSource dataSource, @Value("${app.isDebug}") boolean isDebug) {
        this.dataSource = dataSource;
        this.isDebug = isDebug;
        doAction();
    }

    private void doAction() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("db/migration", "classpath:ru.aborisov.testtask.db.migration");
        if (isDebug) {
            flyway.clean();
        }
        flyway.migrate();
    }
}