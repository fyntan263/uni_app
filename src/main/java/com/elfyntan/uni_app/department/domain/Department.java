package com.elfyntan.uni_app.department.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "departments")
public class Department {
    @Id
    private String id;

    @NotBlank(message = "Department name is mandatory")
    private String departmentName;

    @NotBlank(message = "Location is mandatory")
    private String location;
}