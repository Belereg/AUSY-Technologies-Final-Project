package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Model.DAO.JobCategory;
import com.ausy_technologies.finalproject.Service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobcategories")
public class JobCategoryController {

    @Autowired
    private JobCategoryService jobCategoryService;

    @PostMapping("/saveJobCategory")
    public ResponseEntity<JobCategory> saveJobCategory(@RequestBody JobCategory jobCategory){
        return this.jobCategoryService.saveJobCategory(jobCategory);
    }

    @GetMapping("/getAllJobCategories")
    public ResponseEntity<List<JobCategory>> getAllJobCategories(){
        return this.jobCategoryService.getAllJobCategories();
    }

    @GetMapping("/getJobCategoryById/{id}")
    public ResponseEntity<JobCategory> getJobCategoryById(@PathVariable int id){
        return this.jobCategoryService.getJobCategoryById(id);
    }

    @DeleteMapping("/deleteJobCategory/{id}")
    public ResponseEntity<String> deleteJobCategory(@PathVariable int id){
        return this.jobCategoryService.deleteJobCategory(id);
    }
}
