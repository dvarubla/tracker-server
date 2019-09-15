package ru.aborisov.testtask.resource;

import java.util.Objects;

public class PrivilegeData {
    private String alias;
    private String name;

    public PrivilegeData() {
    }

    public PrivilegeData(String alias, String name) {
        this.alias = alias;
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivilegeData that = (PrivilegeData) o;
        return Objects.equals(alias, that.alias) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, name);
    }
}
