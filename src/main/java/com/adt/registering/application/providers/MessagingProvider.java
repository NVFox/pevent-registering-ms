package com.adt.registering.application.providers;

public interface MessagingProvider {
    void publish(String queue, Object message);
}
