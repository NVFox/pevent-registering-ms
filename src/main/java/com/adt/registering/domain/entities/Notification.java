package com.adt.registering.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String channel;
    private Type type;
    private String title;
    private String message;

    public static Notification info(String channel, String title, String message) {
        return new Notification(channel, Type.INFO, title, message);
    }

    public static Notification success(String channel, String title, String message) {
        return new Notification(channel, Type.SUCCESS, title, message);
    }

    public static Notification warning(String channel, String title, String message) {
        return new Notification(channel, Type.WARNING, title, message);
    }

    public static Notification error(String channel, String title, String message) {
        return new Notification(channel, Type.ERROR, title, message);
    }

    public enum Type {
        INFO,
        SUCCESS,
        WARNING,
        ERROR
    }
}
