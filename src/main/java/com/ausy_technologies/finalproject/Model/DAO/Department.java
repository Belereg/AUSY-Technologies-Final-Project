package com.ausy_technologies.finalproject.Model.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_department")
    private int idDepartment;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Employee> employeeList;

    public Department(int i, String departmenttest) {
        this.idDepartment = i;
        this.name = departmenttest;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public String toString() {
        return "Department{" +
                "idDepartment=" + idDepartment +
                ", name='" + name + '\'' +
                ", employeeList=" + employeeList +
                '}';
    }

    public boolean hasValidName() {
        return this.name != null && !this.name.isBlank();
    }
}
