package com.adt.registering.application.services;

import com.adt.registering.application.providers.MessagePublisher;
import com.adt.registering.domain.entities.Log;
import com.adt.registering.domain.services.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogServiceImpl implements LogService {
    private final MessagePublisher messagePublisher;

    @Value("${messaging.notifications-logs.channel-name}")
    private String logChannel;

    @Override
    public void log(Log log) {
        messagePublisher.publish(logChannel, log);
    }
}
