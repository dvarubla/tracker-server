package ru.aborisov.testtask.resource;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class ExpenseUpdateData {
    private int id;
    private OffsetDateTime recordDate;
    private BigDecimal cost;
    private String description;
    private String comment;
    private int userId;

    public ExpenseUpdateData() {
    }

    public ExpenseUpdateData(
            int id, OffsetDateTime recordDate, BigDecimal cost, String description, String comment, Integer userId
    ) {
        this.id = id;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseUpdateData that = (ExpenseUpdateData) o;
        return id == that.id &&
                userId == that.userId &&
                Objects.equals(recordDate, that.recordDate) &&
                Objects.equals(cost, that.cost) &&
                Objects.equals(description, that.description) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recordDate, cost, description, comment, userId);
    }
}
