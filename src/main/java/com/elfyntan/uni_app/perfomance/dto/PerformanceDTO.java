package com.elfyntan.uni_app.perfomance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PerformanceDTO {
    private String id;

    @NotBlank(message = "Employee ID is mandatory")
    private String employeeId;

    @NotBlank(message = "Review period is mandatory")
    private String reviewPeriod;

    @NotBlank(message = "Performance rating is mandatory")
    private String performanceRating;

    @NotBlank(message = "Comments are mandatory")
    private String comments;

    @NotNull(message = "Review date is mandatory")
    private LocalDate reviewDate;
}