package com.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.company.Form.CreateDepartmentForm;
import com.company.Form.DepartmentFilterForm;
import com.company.Form.UpdateDepartmentForm;
import com.company.entity.Department;
import com.company.repository.IDepartmentRepository;
import com.company.specification.DepartmentSpecification;

@Service
public class DepartmentService implements IDepartmentService {

	@Autowired
	private IDepartmentRepository departmentRepository;

	@Override
	public Page<Department> getAllDepartments(Pageable pageable, DepartmentFilterForm filterForm) {

		Specification<Department> where = DepartmentSpecification.buildWhere(filterForm);
		Page<Department> departmentPage = departmentRepository.findAll(where, pageable);

		// other logic ...

		return departmentPage;
	}

	@Override
	public void createDepartment(CreateDepartmentForm form) throws Exception {

		// validate
		if (departmentRepository.existsByName(form.getName())) throw new Exception("Name already exists!");

		Department department = new Department(form.getName(), form.getTotalMember(), form.getType());
		departmentRepository.save(department);
	}

	@Override
	public void updateDepartment(UpdateDepartmentForm form) throws Exception {
		
		// validate
		if (departmentRepository.existsByName(form.getName())) throw new Exception("Name already exists!");
				
		// change value
		Department department = departmentRepository.findById(form.getId()).get();
		department.setName(form.getName());
		
		departmentRepository.save(department);
		
		// other logic ...
	}

	@Override
	public void deleteDepartment(Integer id){
		departmentRepository.deleteById(id);
		
		// other logic ...
	}
}
