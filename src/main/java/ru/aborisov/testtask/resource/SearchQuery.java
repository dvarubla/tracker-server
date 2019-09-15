package ru.aborisov.testtask.resource;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class SearchQuery {
    @NotNull(message = "Номер страницы не может быть пустым")
    private Integer page;
    @NotNull(message = "Число элементов не может быть пустым")
    private Integer count;
    @NotNull(message = "Запрос не может быть null")
    private String query;

    public SearchQuery() {
    }

    public SearchQuery(int page, int count, String query) {
        this.page = page;
        this.count = count;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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
        SearchQuery that = (SearchQuery) o;
        return page.equals(that.page) &&
                count.equals(that.count) &&
                Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, count, query);
    }
}
