package ru.aborisov.testtask.resource;

import java.time.OffsetDateTime;

public class DatePeriod {

    private OffsetDateTime start;
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
