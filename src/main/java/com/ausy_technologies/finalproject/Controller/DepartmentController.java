package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Model.DAO.Department;
import com.ausy_technologies.finalproject.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/saveDepartment")
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department){
        return this.departmentService.saveDepartment(department);
    }

    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<Department>> getAllDepartments(){
        return this.departmentService.getAllDepartments();
    }

    @DeleteMapping("/deleteDepartmentById/{id}")
    public ResponseEntity<String> deleteDepartmentById(@PathVariable int id){
        return this.departmentService.deleteDepartmentById(id);
    }

    @PutMapping("/updateDepartmentById/{id}")
    public ResponseEntity<String> updateDepartmentById(@RequestBody Department department, @PathVariable int id){
        return this.departmentService.updateDepartmentById(department,id);
    }
}
