package com.company.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Department")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`name`", length = 50, nullable = false, unique = true)
    @NonNull
    private String name;

    @Column(name = "total_member")
    @NonNull
    private Integer totalMember;

    @Column(name = "`type`", nullable = false)
    @Convert(converter = DepartmentTypeConvert.class)
    @NonNull
    private Type type;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

    @OneToMany(mappedBy = "department")
    private List<Account> accounts;

    @NoArgsConstructor
    @Getter
    public enum Type {
        DEV("Dev"), TEST("Test"), ScrumMaster("ScrumMaster"), PM("PM");

        private String value;

        private Type(String value) {
            this.value = value;
        }

        public static Type toEnum(String sqlValue) {
            for (Type type : Type.values()) {
                if (type.getValue().equals(sqlValue)) {
                    return type;
                }
            }
            return null;
        }
    }
}

@Converter(autoApply = true)
class DepartmentTypeConvert implements AttributeConverter<Department.Type, String> {

    @Override
    public String convertToDatabaseColumn(Department.Type type) {
        if (type == null) {
            return null;
        }

        return type.getValue();
    }

    @Override
    public Department.Type convertToEntityAttribute(String sqlValue) {
        if (sqlValue == null) {
            return null;
        }

        return Department.Type.toEnum(sqlValue);
    }

}