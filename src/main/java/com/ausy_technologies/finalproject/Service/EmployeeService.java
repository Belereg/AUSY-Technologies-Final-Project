package com.ausy_technologies.finalproject.Service;

import com.ausy_technologies.finalproject.Audit;
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

    Audit audit = Audit.getInstance();

    public String showEmployee(Employee employee) {
        return "\t\t\t\t{ " + '\n' +
                "\t\t\t\t\tidEmployee=" + employee.getIdEmployee() + ",\n" +
                "\t\t\t\t\tfirstName=" + employee.getFirstName() + ",\n" +
                "\t\t\t\t\tlastName=" + employee.getLastName() + ",\n" +
                "\t\t\t\t\tstartDate=" + employee.getStartDate() + ",\n" +
                "\t\t\t\t\tendDate=" + employee.getEndDate() + ",\n" +
                "\t\t\t\t\tactive=" + employee.isActive() + ",\n" +
                "\t\t\t\t\taddress=" + employee.getAddress() + ",\n" +
                "\t\t\t\t\tCP=" + employee.getCp() + ",\n" +
                "\t\t\t\t\ttelephone=" + employee.getTelephone() + ",\n" +
                "\t\t\t\t\temail=" + employee.getEmail() + ",\n" +
                "\t\t\t\t\tbirthday=" + employee.getBirthday() + ",\n" +
                "\t\t\t\t\tnoChildren=" + employee.isNoChildren() + ",\n" +
                "\t\t\t\t\tsalary=" + employee.getSalary() + ",\n" +
                "\t\t\t\t\tstudies=" + employee.getStudies() + ",\n" +
                "\t\t\t\t\tsocialSecurityNumber=" + employee.getSocialSecurityNumber() + ",\n" +
                "\t\t\t\t\thasDrivingLicense=" + employee.isHasDrivingLicense() + ",\n" +
                "\t\t\t\t\tdepartment=" + employee.getDepartment().getIdDepartment() + ",\n" +
                "\t\t\t\t\tjobCategory=" + employee.getJobCategory().getIdJobCategory() + ",\n" +
                "\t\t\t\t}";
    }

    public ResponseEntity<Employee> addEmployeeTest(Employee employee) {
        Employee employeeSaved = this.employeeRepository.save(employee);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "addEmployeeTest");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeSaved);
    }

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
            audit.writePersonsToFile("Employee added\n" + showEmployee(employee));
            return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(employeeSaved);
        }
    }

    public ResponseEntity<Employee> getEmployee(int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeeById");
        Employee employeeSearched = this.employeeRepository.findById(id);
        if (employeeSearched == null) {
            audit.writePersonsToFile("getEmployee(" + id + ") called (UNSUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        audit.writePersonsToFile("getEmployee(" + id + ") called (SUCCESSFULLY)");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employeeSearched);
    }

    public ResponseEntity<List<Employee>> getEmployeesByJob(int jobCategoryId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeesByJob");
        List<Employee> employeeList = this.employeeRepository.getEmployeesByJobCategoryId(jobCategoryId);

        if (employeeList == null) {
            audit.writePersonsToFile("getEmployeesByJob(" + jobCategoryId + ") called (UNSUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
        }

        audit.writePersonsToFile("getEmployeesByJob(" + jobCategoryId + ") called (SUCCESSFULLY)");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
    }

    public ResponseEntity<List<Employee>> getEmployeesByDepartment(int departmentId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeesByDepartment");
        List<Employee> employeeList = this.employeeRepository.getEmployeeByDepartmentIdDepartment(departmentId);
        if (employeeList == null) {
            audit.writePersonsToFile("getEmployeesByJob(" + departmentId + ") called (UNSUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
        }
        audit.writePersonsToFile("getEmployeesByJob(" + departmentId + ") called (SUCCESSFULLY)");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
    }

    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> allEmployees = this.employeeRepository.findAll();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getAllEmployees");
        audit.writePersonsToFile("getAllEmployees() called");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(allEmployees);
    }

    public ResponseEntity<Employee> updateEmployee(Employee employee, int employeeId, int departmentId, int jobCategoryId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "updateEmployeeById");
        Employee updatedEmployee = this.employeeRepository.findById(employeeId);
        if (updatedEmployee == null) {
            audit.writePersonsToFile("updateEmployee/" + employeeId + "/" +departmentId + "/" + jobCategoryId + ") called (UNSUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }

        Department department = this.departmentRepository.findByIdDepartment(departmentId);
        JobCategory jobCategory = this.jobCategoryRepository.findByIdJobCategory(jobCategoryId);

        if (department == null || jobCategory == null) {

            audit.writePersonsToFile("updateEmployee/" + employeeId + "/" +departmentId + "/" + jobCategoryId + ") called (UNSUCCESSFULLY)");
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
            audit.writePersonsToFile("updateEmployee/" + employeeId + "/" +departmentId + "/" + jobCategoryId + ") called (SUCCESSFULLY)");

            return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(updatedEmployee);
        }
    }

    public ResponseEntity<String> deleteEmployee(int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "updateEmployeeById");
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            audit.writePersonsToFile("deleteEmployee(" + id + ") called (UNSUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        } else {
            this.employeeRepository.deleteById(id);
            audit.writePersonsToFile("deleteEmployee(" + id + ") called (SUCCESSFULLY)");

            return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body("Employee with id " + id + " has been deleted");
        }
    }

    public Employee getEmployeeDtoById(int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeeById");
        Employee employeeSearched = this.employeeRepository.findById(id);
        audit.writePersonsToFile("getEmployeeDtoById(" + id + ") called (SUCCESSFULLY)");
        return employeeSearched;
    }

    public List<Employee> getAllEmployeesForDto() {
        return employeeRepository.findAll();
    }
}
