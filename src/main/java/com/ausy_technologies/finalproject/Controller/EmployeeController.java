package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Model.DAO.Employee;
import com.ausy_technologies.finalproject.Service.EmployeeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/saveEmployee")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        return this.employeeService.saveEmployee(employee);
    }

    @PostMapping("/saveEmployeeByDepartmentAndJobCategory/{departmentid}/{jobcategoryid}")
    public ResponseEntity<Employee> saveEmployeeByDepartmentAndJobCategory(@RequestBody Employee employee, @PathVariable int departmentid, @PathVariable int jobcategoryid){
        return this.employeeService.saveEmployeeByDepartmentAndJobCategory(employee, departmentid,jobcategoryid);
    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id){
        return this.employeeService.getEmployeeById(id);
    }

    @GetMapping("/getEmployeesByDepartmentId/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentId(@PathVariable int departmentId){
        return this.employeeService.getEmployeesByDepartmentId(departmentId);
    }

    @GetMapping("/getEmployeesByJobCategoryId/{jobCategoryId}")
    public ResponseEntity<List<Employee>> getEmployeesByJobCategoryId(@PathVariable int jobCategoryId){
        return this.employeeService.getEmployeesByJobCategoryId(jobCategoryId);
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> geAllEmployees(){
        return this.employeeService.getAllEmployees();
    }

}
