package com.adt.registering.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "logs")
@CompoundIndex(def = "{'level': 1, 'timestamp': -1}")
@Data
public class Log {
    @Id
    private String traceId;

    @Indexed
    private Level level;

    @TextIndexed
    private String message;

    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime timestamp;

    public enum Level {
        INFO,
        WARN,
        ERROR
    }
}
