package com.adt.registering.application.providers;

public interface MessagingPublisher {
    void publish(String channel, Object message);
}
