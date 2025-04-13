package com.adt.registering.application.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventQueryParamsDTO {
    @Pattern(regexp = "^(?!\\s*$).+", message = "Can't be empty or blank")
    private String type;

    private LocalDateTime from;
    private LocalDateTime to;
    private Integer page = 0;
    private Integer size = 10;
}
