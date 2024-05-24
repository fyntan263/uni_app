package com.elfyntan.uni_app.position.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "positions")
public class JobPosition {
    @Id
    private String id;

    @NotBlank(message = "Position name is mandatory")
    private String positionName;

    @NotBlank(message = "Salary range is mandatory")
    private String salaryRange;
}