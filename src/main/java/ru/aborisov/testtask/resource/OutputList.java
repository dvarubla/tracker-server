package ru.aborisov.testtask.resource;

import java.util.List;
import java.util.Objects;

public class OutputList<T> {
    private List<T> items;
    private int count;

    public OutputList(List<T> items) {
        this.items = items;
        this.count = items.size();
    }

    public OutputList() {
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutputList<?> that = (OutputList<?>) o;
        return count == that.count &&
                Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, count);
    }
}
