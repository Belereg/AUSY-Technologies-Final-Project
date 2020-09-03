package com.ausy_technologies.finalproject.Repository;

import com.ausy_technologies.finalproject.Model.DAO.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT u FROM Employee u WHERE u.jobCategory.idJobCategory = ?1")
    public List<Employee> getEmployeesByJobCategoryId(int jobCategoryId);

    List<Employee> getEmployeeByDepartmentIdDepartment(int departmentId);
    Employee findById(int id);
}
