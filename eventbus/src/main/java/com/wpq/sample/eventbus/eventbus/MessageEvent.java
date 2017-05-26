package com.wpq.sample.eventbus.eventbus;

/**
 * @author wpq
 * @version 1.0
 */
public class MessageEvent {

    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
