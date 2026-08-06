package com.zapphon.liveevents.core.contexts;

import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.core.processors.EventProcessor;
import com.zapphon.liveevents.core.queues.EventQueue;

public class EventContext {

    private final EventQueue queue;

    private final EventProcessor processor;

    public EventContext(LiveEvents plugin) {

        this.queue = new EventQueue();

        this.processor =
                new EventProcessor(plugin);

    }

    public EventQueue getQueue() {

        return queue;

    }

    public EventProcessor getProcessor() {

        return processor;

    }

}