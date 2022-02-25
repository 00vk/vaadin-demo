package ru.spb.reshenie.vaadindemo.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table
public class Club extends AbstractEntity {

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "club")
    @Nullable
    private List<Moderator> employees = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Moderator> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Moderator> employees) {
        this.employees = employees;
    }
}
