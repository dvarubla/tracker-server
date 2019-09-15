package ru.aborisov.testtask.resource;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public class ExpenseCreateData {
    private OffsetDateTime recordDate;

    private BigDecimal cost;

    private String description;

    private String comment;

    private Optional<Integer> userId = Optional.empty();

    public ExpenseCreateData() {
    }

    public ExpenseCreateData (
            OffsetDateTime recordDate,
            BigDecimal cost, String description, String comment, Optional<Integer> userId
    ) {
        this.recordDate = recordDate;
        this.cost = cost;
        this.description = description;
        this.comment = comment;
        this.userId = userId;
    }

    public OffsetDateTime getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(OffsetDateTime recordDate) {
        this.recordDate = recordDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Optional<Integer> getUserId() {
        return userId;
    }

    public void setUserId(Optional<Integer> userId) {
        this.userId = userId;
    }
}
