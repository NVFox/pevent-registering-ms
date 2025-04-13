package com.adt.registering.application.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateEventCommand(
        @NotBlank @Size(min = 5)
        String type,
        @NotBlank @Size(min = 5)
        String description
) {
}
