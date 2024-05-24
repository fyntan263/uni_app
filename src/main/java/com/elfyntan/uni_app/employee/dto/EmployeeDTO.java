package com.elfyntan.uni_app.employee.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
public class EmployeeDTO {
    private String id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dob;

    @NotBlank(message = "Gender is mandatory")
    private String gender;

    @NotNull(message = "Hire date is mandatory")
    private LocalDate hireDate;

    @NotNull(message = "Salary is mandatory")
    private Double salary;

    @NotBlank(message = "Contact information is mandatory")
    @Email(message = "Email should be valid")
    private String contactInfo;

    @NotBlank(message = "Department ID is mandatory")
    private String departmentId;

    @NotBlank(message = "Job position ID is mandatory")
    private String jobPositionId;

    private String managerId;

    @NotBlank(message = "Status is mandatory")
    private String status;

    @NotNull(message = "Address is mandatory")
    private AddressDTO address;
}