package com.elfyntan.uni_app.employee.domain;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class Address {
    @NotBlank(message = "Street is mandatory")
    private String street;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "State is mandatory")
    private String state;

    @NotBlank(message = "Postal code is mandatory")
    private String postalCode;
}
