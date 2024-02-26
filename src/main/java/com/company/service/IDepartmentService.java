package com.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.company.Form.CreateDepartmentForm;
import com.company.Form.DepartmentFilterForm;
import com.company.Form.UpdateDepartmentForm;
import com.company.entity.Department;

public interface IDepartmentService {

	Page<Department> getAllDepartments(Pageable pageable, DepartmentFilterForm filterForm);

	void createDepartment(CreateDepartmentForm form) throws Exception;

	void updateDepartment(UpdateDepartmentForm form) throws Exception;

	void deleteDepartment(Integer id);
}
