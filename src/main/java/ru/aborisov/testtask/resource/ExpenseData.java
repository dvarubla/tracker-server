package ru.aborisov.testtask.resource;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class ExpenseData {
    private int id;

    private OffsetDateTime recordDate;

    private BigDecimal cost;

    private String description;

    private String comment;

    private UserNameId user;

    public ExpenseData(int id, OffsetDateTime recordDate, BigDecimal cost, String description, String comment, UserNameId user) {
        this.id = id;
        this.recordDate = recordDate;
        this.cost = cost;
        this.description = description;
        this.comment = comment;
        this.user = user;
    }

    public ExpenseData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UserNameId getUser() {
        return user;
    }

    public void setUser(UserNameId user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseData that = (ExpenseData) o;
        return id == that.id &&
                Objects.equals(recordDate, that.recordDate) &&
                Objects.equals(cost, that.cost) &&
                Objects.equals(description, that.description) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recordDate, cost, description, comment, user);
    }
}
