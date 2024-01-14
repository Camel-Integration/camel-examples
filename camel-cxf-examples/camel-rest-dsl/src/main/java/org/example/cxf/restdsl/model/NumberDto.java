package org.example.cxf.restdsl.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.cxf.restdsl.validation.IsNumeric;

@Data
public class NumberDto {

    @NotNull(message = "Number is required")
    @IsNumeric(message = "Number must be numeric", fieldName = "number")
    private String number;
}
