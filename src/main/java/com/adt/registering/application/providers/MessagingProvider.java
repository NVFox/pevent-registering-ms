package com.adt.registering.application.providers;

public interface MessagingProvider {
    void publish(String channel, Object message);
}
