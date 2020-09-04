package com.ausy_technologies.finalproject.Service;

import com.ausy_technologies.finalproject.Audit;
import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Department;
import com.ausy_technologies.finalproject.Repository.DepartmentRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    Audit audit = Audit.getInstance();

    private String showDepartment(Department department) {
        return "\t\t\t\t{ " + '\n' +
                "\t\t\t\t\tidDepartment=" + department.getIdDepartment() + ",\n" +
                "\t\t\t\t\tname=" + department.getName() + ",\n" +
                "\t\t\t\t}";
    }
    public ResponseEntity<Department> addDepartment(Department department) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "addDepartment");
        Department departmentAdded = null;
        if (!department.hasValidName()) {
            audit.writePersonsToFile("addDepartment called (UNSUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        } else {
            try {
                departmentAdded = this.departmentRepository.save(department);
            } catch (ErrorResponse errorResponse) {
                audit.writePersonsToFile("addDepartment called (UNSUCCESSFULLY)");
                ErrorResponse.LogError(errorResponse);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
            }
            audit.writePersonsToFile("Department added" + showDepartment(department));
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(departmentAdded);
        }
    }

    public ResponseEntity<List<Department>> getAllDepartments() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getAllDepartments");
        List<Department> departmentList = this.departmentRepository.findAll();
        audit.writePersonsToFile("getAllDepartments() called (SUCCESSFULLY)");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(departmentList);
    }

    public ResponseEntity<String> deleteDepartment(int departmentId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "deleteDepartmentById");

        List<Department> allDepartments = this.departmentRepository.findAll();
        List<Integer> allIds = new ArrayList<>();
        for (Department department : allDepartments)
            if (department.getIdDepartment() == departmentId)
                allIds.add(department.getIdDepartment());

        if (!allIds.contains(departmentId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("There is no department with that ID.");
        } else {
            try {
                this.departmentRepository.deleteById(departmentId);
            } catch (ErrorResponse errorResponse) {
                ErrorResponse.LogError(errorResponse);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("There is no department with that ID.");
            }
            audit.writePersonsToFile("deleteDepartment(" + departmentId + ") called");
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Department with id " + departmentId + " has been deleted");
        }
    }

    public ResponseEntity<String> updateDepartmentById(Department department, int departmentId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "deleteDepartmentById");

        List<Department> allDepartments = this.departmentRepository.findAll();
        List<Integer> allIds = new ArrayList<>();
        for (Department dep : allDepartments)
            if (dep.getIdDepartment() == departmentId)
                allIds.add(dep.getIdDepartment());

        if (!allIds.contains(departmentId)) {
            audit.writePersonsToFile("updateDepartmentById(" + departmentId + ") called (UNSUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("There is no department with that ID.");
        } else {
            Department departmentToUpdate = null;
            try {
                departmentToUpdate = this.departmentRepository.findById(departmentId);
                departmentToUpdate.setName(department.getName());
                this.departmentRepository.save(departmentToUpdate);
            } catch (ErrorResponse errorResponse) {
                audit.writePersonsToFile("updateDepartmentById(" + departmentId + ") called (UNSUCCESSFULLY)");
                ErrorResponse.LogError(errorResponse);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
            }
            audit.writePersonsToFile("updateDepartmentById() called (SUCCESSFULLY)" + showDepartment(departmentToUpdate));
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Department with id " + departmentId + " has been updated");
        }
    }

    public ResponseEntity<Department> getDepartmentById(int departmentId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getDepartmentById");
        Department department = departmentRepository.findById(departmentId);
        if (department == null) {
            audit.writePersonsToFile("updateDepartmentById(" + departmentId + ") called (UNSUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        audit.writePersonsToFile("updateDepartmentById(" + departmentId + ") called (SUCCESSFULLY)");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(department);
    }

    public List<Department> addDepartments(@RequestBody List<Department> departments) {
        this.departmentRepository.saveAll(departments);
        return departments;
    }

    public List<Department> getDepartments(@RequestBody List<Department> departments) {
        return departmentRepository.findAll();
    }
}
