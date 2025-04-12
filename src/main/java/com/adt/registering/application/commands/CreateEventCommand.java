package com.adt.registering.application.commands;

public record CreateEventCommand(
        String type,
        String description
) {
}
