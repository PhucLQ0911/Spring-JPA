package com.company.specification;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.company.Form.DepartmentFilterForm;
import com.company.entity.Department;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class DepartmentSpecification {

    @SuppressWarnings("deprecation")
    public static Specification<Department> buildWhere(DepartmentFilterForm filterForm) {

        Specification<Department> where = null;

        if (filterForm == null)
            return where;

        if (!StringUtils.isEmpty(filterForm.getSearch())) {
            String search = filterForm.getSearch();
            search = search.trim();
            CustomSpecification nameSpecification = new CustomSpecification("name", search);
            where = Specification.where(nameSpecification);
        }

        if (filterForm.getMinCreatedDate() != null) {
            CustomSpecification minCreatedDateSpecification = new CustomSpecification("minCreatedDate", filterForm.getMinCreatedDate());
            if (where == null) {
                where = minCreatedDateSpecification;
            } else {
                where = where.and(minCreatedDateSpecification);
            }
        }

        if (filterForm.getMaxCreatedDate() != null) {
            CustomSpecification maxCreatedDateSpecification = new CustomSpecification("maxCreatedDate", filterForm.getMaxCreatedDate());
            if (where == null) {
                where = maxCreatedDateSpecification;
            } else {
                where = where.and(maxCreatedDateSpecification);
            }
        }

        if (filterForm.getMinYear() != null) {
            CustomSpecification minYearSpecification = new CustomSpecification("minYear", filterForm.getMinYear());
            if (where == null) {
                where = minYearSpecification;
            } else {
                where = where.and(minYearSpecification);
            }
        }

        if (filterForm.getType() != null) {
            CustomSpecification typeSpecification = new CustomSpecification("type", filterForm.getType());
            if (where == null) {
                where = typeSpecification;
            } else {
                where = where.and(typeSpecification);
            }
        }

        return where;
    }

    @SuppressWarnings("serial")
    @RequiredArgsConstructor
    static class CustomSpecification implements Specification<Department> {

        @NonNull
        private String field;

        @NonNull
        private Object value;

        @Override
        public Predicate toPredicate(
                Root<Department> root,
                CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) {

            if (field.equalsIgnoreCase("name")) {
                return criteriaBuilder.like(root.get("name"), "%" + value.toString() + "%");
            }

            if (field.equalsIgnoreCase("minCreatedDate")) {
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get("createdDate").as(java.sql.Date.class),
                        (Date) value);
            }

            if (field.equalsIgnoreCase("maxCreatedDate")) {
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get("createdDate").as(java.sql.Date.class),
                        (Date) value);
            }

            if (field.equalsIgnoreCase("minYear")) {
                return criteriaBuilder.greaterThanOrEqualTo(
                        criteriaBuilder.function("YEAR", Integer.class,
                                root.get("createdDate")),
                        (Integer) value);
            }

            if (field.equalsIgnoreCase("type")) {
                return criteriaBuilder.equal(root.get("type"), value);
            }

            return null;
        }
    }

}
