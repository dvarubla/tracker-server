package ru.aborisov.testtask.resource;

import java.util.List;
import java.util.Objects;

public class UserDataPrivilegies {
    private UserPublicData data;
    private List<PrivilegeData> privilegies;

    public UserDataPrivilegies(UserPublicData data, List<PrivilegeData> privilegies) {
        this.data = data;
        this.privilegies = privilegies;
    }

    public UserDataPrivilegies() {
    }

    public UserPublicData getData() {
        return data;
    }

    public void setData(UserPublicData data) {
        this.data = data;
    }

    public List<PrivilegeData> getPrivilegies() {
        return privilegies;
    }

    public void setPrivilegies(List<PrivilegeData> privilegies) {
        this.privilegies = privilegies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataPrivilegies that = (UserDataPrivilegies) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(privilegies, that.privilegies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, privilegies);
    }
}
