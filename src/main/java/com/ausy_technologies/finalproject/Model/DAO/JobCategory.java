package com.ausy_technologies.finalproject.Model.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jobcategories")
public class JobCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_job_category")
    private int idJobCategory;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "jobCategory", fetch = FetchType.LAZY)
    private List<Employee> jobEmployeeList;

    public int getIdJobCategory() {
        return idJobCategory;
    }

    public void setIdJobCategory(int idJobCategory) {
        this.idJobCategory = idJobCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getJobEmployeeList() {
        return jobEmployeeList;
    }

    public void setJobEmployeeList(List<Employee> jobEmployeeList) {
        this.jobEmployeeList = jobEmployeeList;
    }

    @Override
    public String toString() {
        return "JobCategory{" +
                "idJobCategory=" + idJobCategory +
                ", name='" + name + '\'' +
                ", jobEmployeeList=" + jobEmployeeList +
                '}';
    }

    public boolean hasValidName() {
        return this.name != null && !this.name.isBlank();
    }

}
