package com.zapphon.liveevents.core.handlers;

import com.zapphon.liveevents.core.events.BaseEvent;

public interface EventHandler<T extends BaseEvent> {

    void handle(T event);

}