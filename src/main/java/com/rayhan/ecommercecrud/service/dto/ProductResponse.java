package com.rayhan.ecommercecrud.service.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponse(
        Long id,
        String name,
        String sku,
        BigDecimal price,
        Integer quantity,
        Instant createdAt,
        Instant updatedAt
) {}
