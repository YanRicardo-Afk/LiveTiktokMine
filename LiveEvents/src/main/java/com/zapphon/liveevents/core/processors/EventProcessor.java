package com.zapphon.liveevents.core.processors;

import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.core.events.BaseEvent;
import com.zapphon.liveevents.core.events.LightningEvent;
import com.zapphon.liveevents.core.events.MobSpawnEvent;
import com.zapphon.liveevents.core.events.TntEvent;
import com.zapphon.liveevents.core.handlers.EventHandler;
import com.zapphon.liveevents.core.handlers.LightningHandler;
import com.zapphon.liveevents.core.handlers.MobSpawnHandler;
import com.zapphon.liveevents.core.handlers.TntHandler;

import java.util.HashMap;
import java.util.Map;

public class EventProcessor {

    private final Map<Class<? extends BaseEvent>, EventHandler<? extends BaseEvent>>
            handlers = new HashMap<>();

    public EventProcessor(LiveEvents plugin) {

        register(
                MobSpawnEvent.class,
                new MobSpawnHandler(plugin)
        );

        register(
                TntEvent.class,
                new TntHandler()
        );

        register(
                LightningEvent.class,
                new LightningHandler()
        );
    }

    public <T extends BaseEvent> void register(
            Class<T> eventClass,
            EventHandler<T> handler
    ) {
        handlers.put(eventClass, handler);
    }

    public void process(BaseEvent event) {

        EventHandler<? extends BaseEvent> handler =
                handlers.get(event.getClass());

        if (handler == null) {
            throw new IllegalArgumentException(
                    "Nenhum handler registrado para: "
                            + event.getClass().getSimpleName()
            );
        }

        executeHandler(handler, event);
    }

    @SuppressWarnings("unchecked")
    private <T extends BaseEvent> void executeHandler(
            EventHandler<? extends BaseEvent> handler,
            BaseEvent event
    ) {

        EventHandler<T> typedHandler =
                (EventHandler<T>) handler;

        typedHandler.handle((T) event);
    }
}