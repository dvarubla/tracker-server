package ru.aborisov.testtask.resource;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public class ExpenseCreateData {
    @NotNull(message = "Дата не может быть null")
    private OffsetDateTime recordDate;

    @NotNull(message = "Стоимость не может быть null")
    @Min(value = 0, message = "Стоимость всегда больше 0")
    @Max(value = 10000000, message = "Слишком большая стоимость")
    private BigDecimal cost;

    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @NotBlank(message = "Комментарий не может быть пустым")
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
