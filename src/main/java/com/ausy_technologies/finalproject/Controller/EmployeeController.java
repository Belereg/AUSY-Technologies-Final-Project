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
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
//
//    @PostMapping("/addEmployee")
//    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
//        return this.employeeService.addEmployee(employee);
//    }

    @PostMapping("/addEmployee/{departmentid}/{jobcategoryid}")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee, @PathVariable int departmentid, @PathVariable int jobcategoryid) {
        return this.employeeService.addEmployee(employee, departmentid, jobcategoryid);
    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        return this.employeeService.getEmployeeById(id);
    }

    @GetMapping("/getEmployeesByDep/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDep(@PathVariable int departmentId) {
        return this.employeeService.getEmployeesByDep(departmentId);
    }

    @GetMapping("/getEmployeesByJob/{jobCategoryId}")
    public ResponseEntity<List<Employee>> getEmployeesByJob(@PathVariable int jobCategoryId) {
        return this.employeeService.getEmployeesByJob(jobCategoryId);
    }

    @GetMapping("/getAllEmployee")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return this.employeeService.getAllEmployee();
    }

    @PutMapping("/updateEmployeeById/{id}/{idDepartment}/{idJobCategory}")
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee employee, @PathVariable int id, @PathVariable int idDepartment, @PathVariable int idJobCategory){
        return this.employeeService.updateEmployeeById(employee,id,idDepartment,idJobCategory);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id){
        return this.employeeService.deleteEmployee(id);
    }
}
