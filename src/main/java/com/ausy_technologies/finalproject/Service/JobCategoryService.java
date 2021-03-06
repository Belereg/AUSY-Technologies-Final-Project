package com.ausy_technologies.finalproject.Service;

import com.ausy_technologies.finalproject.Audit;
import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Department;
import com.ausy_technologies.finalproject.Model.DAO.Employee;
import com.ausy_technologies.finalproject.Model.DAO.JobCategory;
import com.ausy_technologies.finalproject.Repository.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;

@Service
public class JobCategoryService {

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    Audit audit = Audit.getInstance();

    private String showJobCategories(JobCategory jobCategory) {
        return "\t\t\t\t{ " + '\n' +
                "\t\t\t\t\tidJobCategory=" + jobCategory.getIdJobCategory() + ",\n" +
                "\t\t\t\t\tname=" + jobCategory.getName() + ",\n" +
                "\t\t\t\t}";
    }
    public ResponseEntity<JobCategory> addJobCategory(JobCategory jobCategory) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "addJobCategory");
        JobCategory jobCategoryAdded = null;
        if (!jobCategory.hasValidName()) {
            audit.writePersonsToFile("addJobCategory(" + jobCategory + ") called (UNSUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        else {
            try {
                jobCategoryAdded = this.jobCategoryRepository.save(jobCategory);
            } catch (ErrorResponse errorResponse) {
                audit.writePersonsToFile("addJobCategory(" + jobCategory + ") called (UNSUCCESSFULLY)");
                ErrorResponse.LogError(errorResponse);
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).headers(httpHeaders).body(null);
            }
            audit.writePersonsToFile("addJobCategory(" + showJobCategories(jobCategory) + ") called (SUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(jobCategoryAdded);
        }
    }

    public ResponseEntity<List<JobCategory>> getAllJobCategories() {
        List<JobCategory> allJobCategories = this.jobCategoryRepository.findAll();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getAllJobCategories");
        audit.writePersonsToFile("getAllJobCategories() called (SUCCESSFULLY)");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(allJobCategories);
    }

    public ResponseEntity<JobCategory> getJobCategoryById(int jobCategoryId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeeById");
        JobCategory jobSearched = null;

        try {
            jobSearched = this.jobCategoryRepository.findById(jobCategoryId).get();
        } catch (Exception exception) {
            audit.writePersonsToFile("getJobCategoryById() called (UNSUCCESSFULLY)");
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        audit.writePersonsToFile("getJobCategoryById(" + jobCategoryId + ") called (SUCCESSFULLY)");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(jobSearched);
    }


    public ResponseEntity<String> deleteJobCategory(int jobCategoryId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "deleteJobCategory");
        JobCategory jobCategoryToDelete = this.jobCategoryRepository.findByIdJobCategory(jobCategoryId);

        if (jobCategoryToDelete == null) {
            audit.writePersonsToFile("deleteJobCategory(" + jobCategoryId + ") called (UNSUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("There is no job category with that ID.");
        } else {
            try {
                this.jobCategoryRepository.deleteById(jobCategoryId);
            } catch (ErrorResponse errorResponse) {
                audit.writePersonsToFile("deleteJobCategory(" + jobCategoryId + ") called (UNSUCCESSFULLY)");
                ErrorResponse.LogError(errorResponse);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("There is no job category with that ID.");
            }
            audit.writePersonsToFile("deleteJobCategory(" + jobCategoryId + ") called (SUCCESSFULLY)");
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Job Category with id " + jobCategoryId + " has been deleted");
        }
    }
}
