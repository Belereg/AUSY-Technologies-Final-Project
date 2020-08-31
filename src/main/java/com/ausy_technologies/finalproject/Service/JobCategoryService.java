package com.ausy_technologies.finalproject.Service;

import com.ausy_technologies.finalproject.Model.DAO.JobCategory;
import com.ausy_technologies.finalproject.Repository.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobCategoryService {

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    public JobCategory saveJobCategory(JobCategory jobCategory){
        return this.jobCategoryRepository.save(jobCategory);
    }
}
