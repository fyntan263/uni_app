package com.elfyntan.uni_app.position.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobPositionDTO {
    @NotBlank(message = "Position name is mandatory")
    private String positionName;

    @NotBlank(message = "Salary range is mandatory")
    private String salaryRange;
}
