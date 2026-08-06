package com.zapphon.liveevents.core.engine;

import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.core.contexts.EventContext;
import com.zapphon.liveevents.core.events.BaseEvent;

public class LiveEngine {

    private final EventContext context;

    private BaseEvent currentEvent;
    private int remainingDelay;

    public LiveEngine(LiveEvents plugin) {
        this.context = new EventContext(plugin);
    }

    public void submit(BaseEvent event) {
        context.getQueue().add(event);
    }

    public void tick() {

        if (currentEvent == null) {

            if (context.getQueue().isEmpty()) {
                return;
            }

            currentEvent = context.getQueue().poll();
            remainingDelay = currentEvent.getDelayTicks();
        }

        if (remainingDelay > 0) {
            remainingDelay--;
            return;
        }

        context.getProcessor().process(currentEvent);

        currentEvent = null;
    }
}