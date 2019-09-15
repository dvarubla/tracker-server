package ru.aborisov.testtask.dao;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privilegeId")
    @SequenceGenerator(
            name = "privilegeId", sequenceName = "privilegeId",
            allocationSize = 1
    )
    private int id;

    public Privilege() {
    }

    public Privilege(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    private String name;

    @NaturalId
    private String alias;

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;

    public int getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Privilege privilege = (Privilege) o;
        return Objects.equals(alias, privilege.alias);
    }

    public int hashCode() {
        return Objects.hash(alias);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
