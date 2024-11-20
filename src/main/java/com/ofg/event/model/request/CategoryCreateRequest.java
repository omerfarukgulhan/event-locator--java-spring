package com.ofg.event.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(
        @NotBlank(message = "{app.constraint.category-name.not-blank}")
        @Size(min = 3, max = 50)
        String name
) {

}