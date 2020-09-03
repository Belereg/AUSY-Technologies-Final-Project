package com.ausy_technologies.finalproject.Repository;

import com.ausy_technologies.finalproject.Model.DAO.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Department findByIdDepartment(int id);

    Department findById(int id);
}
