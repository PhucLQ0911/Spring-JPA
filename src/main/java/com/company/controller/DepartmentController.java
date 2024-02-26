package com.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.company.Form.CreateDepartmentForm;
import com.company.Form.DepartmentFilterForm;
import com.company.Form.UpdateDepartmentForm;
import com.company.entity.Department;
import com.company.service.IDepartmentService;

@Controller
public class DepartmentController {

	@Autowired
	private IDepartmentService departmentService;

	public Page<Department> getAllDepartments(Pageable pageable, DepartmentFilterForm filterForm) {
		// TODO validate filter form
		return departmentService.getAllDepartments(pageable, filterForm);
	}

	public void createDepartment(CreateDepartmentForm form) throws Exception {
		// TODO validate form
		departmentService.createDepartment(form);
	}

	public void updateDepartment(UpdateDepartmentForm form) throws Exception {
		// TODO validate form
		departmentService.updateDepartment(form);
	}

	public void deleteDepartment(Integer id) {
		departmentService.deleteDepartment(id);
	}
}
