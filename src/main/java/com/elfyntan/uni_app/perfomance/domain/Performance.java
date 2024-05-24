package com.elfyntan.uni_app.perfomance.domain;

import com.elfyntan.uni_app.employee.domain.Employee;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "performances")
public class Performance {
    @Id
    private String id;

    @DBRef
    @NotNull(message = "Employee is mandatory")
    private Employee employee;

    @NotBlank(message = "Review period is mandatory")
    private String reviewPeriod;

    @NotBlank(message = "Performance rating is mandatory")
    private String performanceRating;

    @NotBlank(message = "Comments are mandatory")
    private String comments;

    @NotNull(message = "Review date is mandatory")
    private LocalDate reviewDate;
}