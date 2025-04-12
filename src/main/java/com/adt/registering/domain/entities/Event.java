package com.adt.registering.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "events")
@CompoundIndex(def = "{'type': 1, 'createdAt': -1}")
@Data
public class Event {
    @Id
    private String id;

    @Indexed
    private String type;

    private String description;

    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime createdAt;
}
