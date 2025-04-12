package com.adt.registering.infrastructure.persistence.db;

import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.repositories.EventRepository;
import com.adt.registering.domain.repositories.filters.EventFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoEventRepository implements EventRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Event> save(Event event) {
        return mongoTemplate.save(event);
    }

    @Override
    public Mono<Page<Event>> findBy(EventFilter filter, Pageable pageable) {
        Criteria criteria = new Criteria();

        if (filter.type() != null)
            criteria = criteria.and("type").is(filter.type());

        if (filter.from() != null)
            criteria = criteria.and("createdAt").gte(filter.from());

        if (filter.to() != null)
            criteria = criteria.and("createdAt").lte(filter.to());

        Query query = new Query(criteria)
                .with(pageable);

        return mongoTemplate.find(query, Event.class)
                .collectList()
                .zipWith(mongoTemplate.count(query, Event.class),
                        (events, count) -> new PageImpl<>(events, pageable, count));
    }
}
