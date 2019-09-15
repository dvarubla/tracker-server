package ru.aborisov.testtask.resource;

import java.math.BigDecimal;
import java.util.Objects;

public class MoneyValue {
    private BigDecimal value;

    public MoneyValue() {
    }

    public MoneyValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyValue that = (MoneyValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
