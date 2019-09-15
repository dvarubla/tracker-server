package ru.aborisov.testtask.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class ExpenseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expenseId")
    @SequenceGenerator(
            name = "expenseId", sequenceName = "expenseId",
            allocationSize = 1
    )

    private int id;

    private OffsetDateTime recordDate;

    private BigDecimal cost;

    private String description;

    private String comment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "appUserId")
    private AppUser appUser;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public ExpenseRecord(OffsetDateTime recordDate, BigDecimal cost, String description, String comment, AppUser appUser) {
        this.recordDate = recordDate;
        this.cost = cost;
        this.description = description;
        this.comment = comment;
        this.appUser = appUser;
    }

    public ExpenseRecord() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseRecord that = (ExpenseRecord) o;
        return id == that.id &&
                Objects.equals(recordDate, that.recordDate) &&
                Objects.equals(cost, that.cost) &&
                Objects.equals(description, that.description) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(appUser, that.appUser);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

