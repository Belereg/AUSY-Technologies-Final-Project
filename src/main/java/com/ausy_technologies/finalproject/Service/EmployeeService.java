package com.ausy_technologies.finalproject.Service;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Department;
import com.ausy_technologies.finalproject.Model.DAO.Employee;
import com.ausy_technologies.finalproject.Model.DAO.JobCategory;
import com.ausy_technologies.finalproject.Repository.DepartmentRepository;
import com.ausy_technologies.finalproject.Repository.EmployeeRepository;
import com.ausy_technologies.finalproject.Repository.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
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

    public ResponseEntity<Employee> addEmployee(Employee employee, int departmentId, int jobCategoryId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "addEmployeeByDepartmentAndJob");

        Department department = this.departmentRepository.findByIdDepartment(departmentId);
        JobCategory jobCategory = this.jobCategoryRepository.findByIdJobCategory(jobCategoryId);

        if (department == null || jobCategory == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        } else {
            employee.setDepartment(department);
            employee.setJobCategory(jobCategory);
            Employee employeeSaved = this.employeeRepository.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(employeeSaved);
        }
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Response", "addEmployeeByDepartmentAndJob");
//
//        //Department department = this.departmentRepository.findByIdDepartment(departmentId);
//        List<Department> allDepartments = this.departmentRepository.findAll();
//        List<Integer> allIds = new ArrayList<>();
//        List<Department> allJobCategories = this.departmentRepository.findAll();
//        List<Integer> allIdsJobs = new ArrayList<>();
//        for (Department dep : allDepartments)
//            if (dep.getIdDepartment() == departmentId)
//                allIds.add(dep.getIdDepartment());
//        for (Department job : allJobCategories)
//            if (job.getIdDepartment() == departmentId)
//                allIdsJobs.add(job.getIdDepartment());
//        if (!allIds.contains(departmentId) || !allIdsJobs.contains(jobCategoryId)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
//        }
//        Department department = departmentRepository.findById(departmentId).get();
//        JobCategory jobCategory = jobCategoryRepository.findById(jobCategoryId).get();
//        employee.setDepartment(department);
//        employee.setJobCategory(jobCategory);
//        Employee employeeSaved = this.employeeRepository.save(employee);
//        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(employeeSaved);
    }

    public ResponseEntity<Employee> getEmployee(int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeeById");
        Employee employeeSearched = this.employeeRepository.findById(id);
        if (employeeSearched == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employeeSearched);
    }

    public ResponseEntity<List<Employee>> getEmployeesByJob(int jobCategoryId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeesByJob");
        List<Employee> employeeList = this.employeeRepository.getEmployeesByJobCategoryId(jobCategoryId);

        if (employeeList == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
    }

    public ResponseEntity<List<Employee>> getEmployeesByDepartment(int departmentId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeesByDepartment");
        List<Employee> employeeList = this.employeeRepository.getEmployeeByDepartmentIdDepartment(departmentId);
        if (employeeList == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
    }

    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> allEmployees = this.employeeRepository.findAll();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getAllEmployees");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(allEmployees);
    }

    public ResponseEntity<Employee> updateEmployee(Employee employee, int employeeId, int departmentId, int jobCategoryId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "updateEmployeeById");
        Employee updatedEmployee = this.employeeRepository.findById(employeeId);
        if (updatedEmployee == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);

        Department department = this.departmentRepository.findByIdDepartment(departmentId);
        JobCategory jobCategory = this.jobCategoryRepository.findByIdJobCategory(jobCategoryId);

        if (department == null || jobCategory == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        } else {
            updatedEmployee.setDepartment(department);
            updatedEmployee.setJobCategory(jobCategory);
            updatedEmployee.setFirstName(employee.getFirstName());
            updatedEmployee.setLastName(employee.getLastName());
            updatedEmployee.setActive(employee.isActive());
            updatedEmployee.setAddress(employee.getAddress());
            updatedEmployee.setBirthday(employee.getBirthday());
            updatedEmployee.setCp(employee.getCp());
            updatedEmployee.setEmail(employee.getEmail());
            updatedEmployee.setStartDate(employee.getStartDate());
            updatedEmployee.setEndDate(employee.getEndDate());
            updatedEmployee.setHasDrivingLicense(employee.isHasDrivingLicense());
            updatedEmployee.setManager(employee.isManager());
            updatedEmployee.setNoChildren(employee.isNoChildren());
            updatedEmployee.setSalary(employee.getSalary());
            updatedEmployee.setSocialSecurityNumber(employee.getSocialSecurityNumber());
            updatedEmployee.setStudies(employee.getStudies());
            updatedEmployee.setTelephone(employee.getTelephone());

            employeeRepository.save(updatedEmployee);
            return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(updatedEmployee);
        }
    }

    public ResponseEntity<String> deleteEmployee(int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "updateEmployeeById");
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        } else {
            this.employeeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body("Employee with id " + id + " has been deleted");
        }
    }

    public Employee getEmployeeDtoById(int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeeById");
        Employee employeeSearched = this.employeeRepository.findById(id);
        if (employeeSearched == null) {

        }
        return employeeSearched;
    }

    public List<Employee> getAllEmployeesForDto() {
        return employeeRepository.findAll();
    }
}
