package com.rayhan.ecommercecrud.service.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank @Size(max = 180) String name,
        @NotBlank @Size(max = 80) String sku,
        @NotNull @DecimalMin(value = "0.00", inclusive = false) @Digits(integer = 17, fraction = 2) BigDecimal price,
        @NotNull @Min(0) Integer quantity
) {}
