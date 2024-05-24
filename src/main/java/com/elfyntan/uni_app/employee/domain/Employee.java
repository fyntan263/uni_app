package com.elfyntan.uni_app.employee.domain;


import java.time.LocalDate;

import com.elfyntan.uni_app.department.domain.Department;
import com.elfyntan.uni_app.position.domain.JobPosition;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Data
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Gender is mandatory")
    private String gender;

    @NotNull(message = "Hire date is mandatory")
    private LocalDate hireDate;

    @DBRef
    @NotNull(message = "Department is mandatory")
    private Department department;

    @DBRef
    @NotNull(message = "Position is mandatory")
    private JobPosition jobPosition;

    @DBRef
    private Employee manager; // nullable

    @NotNull(message = "Salary is mandatory")
    @Positive(message = "Salary must be positive")
    private Double salary;

    @Email(message = "Contact info must be a valid email")
    private String contactInfo;

    @NotNull(message = "Address is mandatory")
    private Address address;

    @NotBlank(message = "Status is mandatory")
    private String status;
}
