package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Model.DAO.Department;
import com.ausy_technologies.finalproject.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/addDepartment")
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department){
        return this.departmentService.addDepartment(department);
    }

    @GetMapping("/getDepartmentById/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id){
        return this.departmentService.getDepartmentById(id);
    }

    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<Department>> getAllDepartments(){
        return this.departmentService.getAllDepartments();
    }

    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable int id){
        return this.departmentService.deleteDepartment(id);
    }

    @PutMapping("/updateDepartmentById/{id}")
    public ResponseEntity<String> updateDepartmentById(@RequestBody Department department, @PathVariable int id){
        return this.departmentService.updateDepartmentById(department,id);
    }
}
