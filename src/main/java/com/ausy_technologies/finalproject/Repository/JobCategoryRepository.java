package com.ausy_technologies.finalproject.Repository;

import com.ausy_technologies.finalproject.Model.DAO.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer> {

    public JobCategory findByIdJobCategory(int id);
}
