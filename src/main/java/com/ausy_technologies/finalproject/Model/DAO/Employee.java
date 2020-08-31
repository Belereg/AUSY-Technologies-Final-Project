package com.ausy_technologies.finalproject.Model.DAO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private int idEmployee;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_manager")
    private boolean isManager;

    @Column(name = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @Column(name = "active")
    private boolean active;

    @Column(name = "address")
    private String address;

    @Column(name = "CP")
    private String cp;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "no_children")
    private boolean noChildren;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "studies")
    private String studies;

    @Column(name = "social_security_number")
    private String socialSecurityNumber;

    @Column(name = "has_driving_license")
    private boolean hasDrivingLicense;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_department")
    private Department department;

    //@JsonBackReference
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_job_category")
    private JobCategory jobCategory;

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isNoChildren() {
        return noChildren;
    }

    public void setNoChildren(boolean noChildren) {
        this.noChildren = noChildren;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getStudies() {
        return studies;
    }

    public void setStudies(String studies) {
        this.studies = studies;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public boolean isHasDrivingLicense() {
        return hasDrivingLicense;
    }

    public void setHasDrivingLicense(boolean hasDrivingLicense) {
        this.hasDrivingLicense = hasDrivingLicense;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public JobCategory getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(JobCategory jobCategory) {
        this.jobCategory = jobCategory;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "idEmployee=" + idEmployee +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isManager=" + isManager +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", active=" + active +
                ", address='" + address + '\'' +
                ", CP='" + cp + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", noChildren=" + noChildren +
                ", salary=" + salary +
                ", studies='" + studies + '\'' +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", hasDrivingLicense=" + hasDrivingLicense +
                ", department=" + department +
                ", jobCategory=" + jobCategory +
                '}';
    }
}
