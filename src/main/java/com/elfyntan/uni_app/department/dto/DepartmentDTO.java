package com.elfyntan.uni_app.department.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
@Data
public class DepartmentDTO {
    @NotBlank(message = "Department name is mandatory")
    private String departmentName;

    @NotBlank(message = "Location is mandatory")
    private String location;
}
