package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Mapper.EmployeeMapper;
import com.ausy_technologies.finalproject.Model.DAO.Employee;
import com.ausy_technologies.finalproject.Model.DTO.EmployeeDto;
import com.ausy_technologies.finalproject.Service.EmployeeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/addEmployeeTest")
    public ResponseEntity<Employee> addEmployeeTest(@RequestBody Employee employee) {
            return this.employeeService.addEmployeeTest(employee);
    }

    @PostMapping("/addEmployee/{departmentid}/{jobcategoryid}")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee, @PathVariable int departmentid, @PathVariable int jobcategoryid) {
        return this.employeeService.addEmployee(employee, departmentid, jobcategoryid);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
        return this.employeeService.getEmployee(id);
    }

    @GetMapping("/getEmployeesByDepartment/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable int departmentId) {
        return this.employeeService.getEmployeesByDepartment(departmentId);
    }

    @GetMapping("/getEmployeesByJob/{jobCategoryId}")
    public ResponseEntity<List<Employee>> getEmployeesByJob(@PathVariable int jobCategoryId) {
        return this.employeeService.getEmployeesByJob(jobCategoryId);
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }

    @PutMapping("/updateEmployee/{id}/{idDepartment}/{idJobCategory}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int id, @PathVariable int idDepartment, @PathVariable int idJobCategory) {
        return this.employeeService.updateEmployee(employee, id, idDepartment, idJobCategory);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        return this.employeeService.deleteEmployee(id);
    }

    @GetMapping("/getEmployeeDTO/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeDTO(@PathVariable int id) {
        EmployeeDto employeeDTO = null;
        Employee employee = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeeDTO");
        employee = employeeService.getEmployeeDtoById(id);
        if (employee == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);

        employeeDTO = employeeMapper.convertEmployeeToDto(employee);
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employeeDTO);
    }

    @GetMapping("/getAllEmployeesDTO")
    public ResponseEntity<List<EmployeeDto>> getEmployeesDTO() {
        List<EmployeeDto> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve", "getAllEmployees");

        try {
            employeeList = employeeService.getAllEmployeesForDto();
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }
}
