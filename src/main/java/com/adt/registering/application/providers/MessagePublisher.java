package com.adt.registering.application.providers;

public interface MessagePublisher {
    void publish(String channel, Object message);
}
