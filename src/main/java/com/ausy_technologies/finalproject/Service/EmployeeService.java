package com.ausy_technologies.finalproject.Service;

import com.ausy_technologies.finalproject.Model.DAO.Department;
import com.ausy_technologies.finalproject.Model.DAO.Employee;
import com.ausy_technologies.finalproject.Model.DAO.JobCategory;
import com.ausy_technologies.finalproject.Repository.DepartmentRepository;
import com.ausy_technologies.finalproject.Repository.EmployeeRepository;
import com.ausy_technologies.finalproject.Repository.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.sound.midi.SysexMessage;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarOutputStream;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    public ResponseEntity<Employee> saveEmployee(Employee employee) {
        Employee employeeSaved = this.employeeRepository.save(employee);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "saveEmployee");
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(employeeSaved);
    }

    public ResponseEntity<Employee> saveEmployeeByDepartmentAndJobCategory(Employee employee, int departmentid, int jobcategoryid) {
        Department department = departmentRepository.findById(departmentid).get();
        JobCategory jobCategory = jobCategoryRepository.findById(jobcategoryid).get();
        employee.setDepartment(department);
        employee.setJobCategory(jobCategory);
        Employee employeeSaved = this.employeeRepository.save(employee);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "saveEmployeeByDepartmentAndJobCategory");
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(employeeSaved);
    }

    public ResponseEntity<Employee> getEmployeeById(int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployee");
        Employee employeeSearched = null;

        try {
            employeeSearched = this.employeeRepository.findById(id).get();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employeeSearched);
    }

    public ResponseEntity<List<Employee>> getEmployeesByJobCategoryId(int jobCategoryId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeesByJobCategoryId");
        List<Employee> employeeList = this.employeeRepository.getEmployeesByJobCategoryId(jobCategoryId);

        if (employeeList == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employeeList);
    }

    public ResponseEntity<List<Employee>> getEmployeesByDepartmentId(int departmentId) {
        List<Department> allDepartments = departmentRepository.findAll();
        List<Integer> departmentIds = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeesByDepartmentId");

        for (Department department : allDepartments) {
            departmentIds.add(department.getIdDepartment());
        }

        if (!departmentIds.contains(departmentId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        else {
            List<Employee> allEmployees = employeeRepository.findAll();
            List<Employee> searchedEmployeeList = new ArrayList<>();
            for (Employee employee : allEmployees)
                if (employee.getDepartment() != null && employee.getDepartment().getIdDepartment() == departmentId)
                    searchedEmployeeList.add(employee);

            return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(searchedEmployeeList);
        }
    }

    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> allEmployees = this.employeeRepository.findAll();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getAllEmployees");
        if (allEmployees.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(allEmployees);
    }

//    public ResponseEntity<Employee> updateEmployeeById(int employeeId) {
//        Employee employeeToUpdate = employeeRepository.findById(employeeId).get();
//
//        if (employeeToUpdate != null) {
//
//            employeeToUpdate.set
//        } else {
//
//        }
//
//    }
}
