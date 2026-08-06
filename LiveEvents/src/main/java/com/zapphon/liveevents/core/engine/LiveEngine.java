package com.zapphon.liveevents.core.engine;

import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.core.contexts.EventContext;
import com.zapphon.liveevents.core.events.BaseEvent;

public class LiveEngine {

    private final EventContext context;

    public LiveEngine(LiveEvents plugin) {

        this.context = new EventContext(plugin);

    }

    public void submit(BaseEvent event) {

        context
                .getQueue()
                .add(event);

    }

    public void tick() {

        if (context.getQueue().isEmpty()) {
            return;
        }

        BaseEvent event =
                context
                        .getQueue()
                        .poll();

        context
                .getProcessor()
                .process(event);

    }

}