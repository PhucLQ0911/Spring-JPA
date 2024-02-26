package com.company.Form;

import java.util.Date;

import com.company.entity.Department;

import lombok.Data;

@Data
public class DepartmentFilterForm {

	private String search;

	private Date minCreatedDate;

	private Date maxCreatedDate;

	private Integer minYear;

	private Department.Type type;
}
