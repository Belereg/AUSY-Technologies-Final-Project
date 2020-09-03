package com.ausy_technologies.finalproject.Mapper;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Employee;
import com.ausy_technologies.finalproject.Model.DTO.EmployeeDto;
import com.ausy_technologies.finalproject.Repository.DepartmentRepository;
import com.ausy_technologies.finalproject.Repository.EmployeeRepository;
import com.ausy_technologies.finalproject.Repository.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    JobCategoryRepository jobCategoryRepository;

    public EmployeeDto convertEmployeeToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        if (employee != null) {
            employeeDto.setFirstName(employee.getFirstName());
            employeeDto.setLastName(employee.getLastName());
            employeeDto.setEmail(employee.getEmail());
            employeeDto.setTelephone(employee.getTelephone());
            employeeDto.setIdEmployee(employee.getIdEmployee());
            employeeDto.setActive(employee.isActive());
            employeeDto.setEndDate(employee.getEndDate());
            employeeDto.setStartDate(employee.getStartDate());
            employeeDto.setManager(employee.isManager());
            employeeDto.setSalary(employee.getSalary());
            try {
                employeeDto.setDepartmentId(employee.getDepartment().getIdDepartment());
            } catch (NullPointerException exception) {
                ErrorResponse.LogError(new ErrorResponse(exception, "Department is null", 206));
                employeeDto.setDepartmentId(0);
            }
            try {
                employeeDto.setJobCategoryId(employee.getJobCategory().getIdJobCategory());
            } catch (NullPointerException exception) {
                ErrorResponse.LogError(new ErrorResponse(exception, "JobCategory is null", 206));
                employeeDto.setJobCategoryId(0);
            }
        } else {
            throw new ErrorResponse("Can not convert a null to EmployeeDTO", 400);
        }
        return employeeDto;
    }
}
