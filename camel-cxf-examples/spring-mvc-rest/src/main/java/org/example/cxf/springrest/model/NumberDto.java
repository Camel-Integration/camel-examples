package org.example.cxf.springrest.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NumberDto {
    @NotBlank
    @Size(min = 1, max = 10)
    private String number;
}
