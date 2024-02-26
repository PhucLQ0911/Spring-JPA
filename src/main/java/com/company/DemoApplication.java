package com.company;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.company.Form.CreateDepartmentForm;
import com.company.Form.DepartmentFilterForm;
import com.company.Form.UpdateDepartmentForm;
import com.company.controller.DepartmentController;
import com.company.entity.Department;
import com.company.entity.Department.Type;

@SpringBootApplication
public class DemoApplication {

    private static ApplicationContext context;

    public static <T> T getBean(Class<T> bean) {
        return context.getBean(bean);
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
        context = SpringApplication.run(DemoApplication.class, args);

        DepartmentController departmentController = getBean(DepartmentController.class);

        System.out.println("\n\n***********GET ALL DEPARTMENTS***********");
        // paging & sorting
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdDate").descending());
        // search & filter
        DepartmentFilterForm filterForm = new DepartmentFilterForm();
        filterForm.setSearch("a");
        filterForm.setMaxCreatedDate(new Date(120, 3, 8));
        filterForm.setType(Type.DEV);
        // get all
        Page<Department> departmentPage = departmentController.getAllDepartments(pageable, filterForm);
        // print result
        System.out.println("Count: " + departmentPage.getTotalElements());
        for (Department department : departmentPage.getContent()) {
            System.out.println(
                    department.getId()
                            + " - " + department.getName()
                            + " - " + department.getType()
                            + " - " + department.getCreatedDate());
        }

        System.out.println("\n\n***********CREATE DEPARTMENTS***********");
        CreateDepartmentForm createForm = new CreateDepartmentForm("new name", 10, Type.DEV);
        departmentController.createDepartment(createForm);

        System.out.println("\n\n***********UPDATE DEPARTMENTS***********");
        UpdateDepartmentForm updateForm = new UpdateDepartmentForm(1, "new name 2");
        departmentController.updateDepartment(updateForm);

        System.out.println("\n\n***********DELETE DEPARTMENTS***********");
        departmentController.deleteDepartment(10);
    }
}