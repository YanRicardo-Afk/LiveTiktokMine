package com.zapphon.liveevents.core.processors;

import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.core.events.ParticleEvent;
import com.zapphon.liveevents.core.events.SoundEvent;
import com.zapphon.liveevents.core.events.TitleEvent;
import com.zapphon.liveevents.core.handlers.ParticleHandler;
import com.zapphon.liveevents.core.handlers.SoundHandler;
import com.zapphon.liveevents.core.handlers.TitleHandler;
import com.zapphon.liveevents.core.events.WolfFollowEvent;
import com.zapphon.liveevents.core.handlers.WolfFollowHandler;
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
                WolfFollowEvent.class,
                new WolfFollowHandler()
        );

        register(
                LightningEvent.class,
                new LightningHandler()
        );
        register(
        TitleEvent.class,
        new TitleHandler()
);

register(
        SoundEvent.class,
        new SoundHandler()
);

register(
        ParticleEvent.class,
        new ParticleHandler()
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