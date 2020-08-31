package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Model.DAO.JobCategory;
import com.ausy_technologies.finalproject.Service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobcategories")
public class JobCategoryController {

    @Autowired
    private JobCategoryService jobCategoryService;

    @PostMapping("/saveJobCategory")
    public JobCategory saveJobCategory(@RequestBody JobCategory jobCategory){
        return this.jobCategoryService.saveJobCategory(jobCategory);
    }
}
