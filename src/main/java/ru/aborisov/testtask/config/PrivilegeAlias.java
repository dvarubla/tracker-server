package ru.aborisov.testtask.config;

public enum PrivilegeAlias {
    MANAGE_ADMINS("manage_admins"),
    MANAGE_MANAGERS("manage_managers"),
    MANAGE_USERS("manage_users"),
    MANAGE_OWN_RECORDS("manage_own_records"),
    MANAGE_OTHER_RECORDS("manage_other_records"),
    CREATE_USER("create_user");

    private String alias;

    PrivilegeAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
