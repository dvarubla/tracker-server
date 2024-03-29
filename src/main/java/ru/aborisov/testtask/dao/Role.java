package ru.aborisov.testtask.dao;

import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleId")
    @SequenceGenerator(
            name = "roleId", sequenceName = "roleId",
            allocationSize = 1
    )
    private int id;

    private String name;

    @NaturalId
    private String alias;

    public Role() {
    }

    public Role(String name, String alias, Set<AppUser> users, Set<Privilege> privileges, int id) {
        this.name = name;
        this.alias = alias;
        this.users = users;
        this.privileges = privileges;
        this.id = id;
    }

    public Role(String name, String alias, Set<AppUser> users, Set<Privilege> privileges) {
        this.name = name;
        this.alias = alias;
        this.users = users;
        this.privileges = privileges;
    }

    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<AppUser> users;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "RolePrivilege",
            joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "privilegeId")
    )
    private Set<Privilege> privileges;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AppUser> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    public Set<Privilege> getPrivileges() {
        return Collections.unmodifiableSet(privileges);
    }

    public int hashCode() {
        return Objects.hash(name);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
