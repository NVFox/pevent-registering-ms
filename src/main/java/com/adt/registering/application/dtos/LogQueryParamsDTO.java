package com.adt.registering.application.dtos;

import com.adt.registering.domain.entities.Log;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogQueryParamsDTO {
    @Pattern(regexp = "^(?!\\s*$).+", message = "Can't be empty or blank")
    private String traceId;

    private Log.Level level;

    @Pattern(regexp = "^(?!\\s*$).+", message = "Can't be empty or blank")
    private String message;

    private LocalDateTime from;
    private LocalDateTime to;
    private Integer page = 0;
    private Integer size = 10;
}
