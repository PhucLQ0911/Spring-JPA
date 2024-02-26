package com.company.Form;

import com.company.entity.Department.Type;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDepartmentForm {

	private String name;

	private Integer totalMember;

	private Type type;
}
