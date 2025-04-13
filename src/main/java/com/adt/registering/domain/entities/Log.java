package com.adt.registering.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    private String traceId;
    private Level level;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public static Log info(String message) {
        Log log = new Log();

        log.setLevel(Level.INFO);
        log.setMessage(message);

        return log;
    }

    public static Log error(String message) {
        Log log = new Log();

        log.setLevel(Level.ERROR);
        log.setMessage(message);

        return log;
    }

    public enum Level {
        INFO,
        ERROR
    }
}
