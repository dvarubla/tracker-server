package ru.aborisov.testtask.resource;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class DatePeriod {
    @NotNull(message = "Дата начала не может быть null")
    private OffsetDateTime start;

    @NotNull(message = "Дата конца не может быть null")
    private OffsetDateTime end;

    public DatePeriod() {
    }

    public DatePeriod(OffsetDateTime start, OffsetDateTime end) {
        this.start = start;
        this.end = end;
    }


    public OffsetDateTime getStart() {
        return start;
    }

    public void setStart(OffsetDateTime start) {
        this.start = start;
    }

    public OffsetDateTime getEnd() {
        return end;
    }

    public void setEnd(OffsetDateTime end) {
        this.end = end;
    }
}
