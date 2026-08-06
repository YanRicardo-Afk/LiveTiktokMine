package com.zapphon.liveevents.managers;

import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.actions.Action;
import com.zapphon.liveevents.actions.LightningAction;
import com.zapphon.liveevents.actions.SpawnMobAction;
import com.zapphon.liveevents.actions.SpawnTntAction;
import com.zapphon.liveevents.models.EventType;
import com.zapphon.liveevents.models.LiveEvent;
import com.zapphon.liveevents.queue.EventQueue;

public class EventManager {

    private final LiveEvents plugin;
    private final EventQueue queue = new EventQueue();

    public EventManager(LiveEvents plugin) {
        this.plugin = plugin;
    }

    public void execute(Action action) {
        action.execute();
    }

    public void submit(LiveEvent event) {

        queue.add(event);

        processNext();

    }

    private void processNext() {

        if (queue.isEmpty()) {
            return;
        }

        LiveEvent event = queue.poll();

        switch (event.getEventType()) {

            case SPAWN_MOB -> execute(
                    new SpawnMobAction(
                            plugin,
                            event.getTargetPlayer(),
                            event.getEntityType(),
                            event.getAmount(),
                            event.getViewerName()
                    )
            );

            case TNT -> execute(
                    new SpawnTntAction(
                            event.getTargetPlayer(),
                            event.getAmount()
                    )
            );

            case LIGHTNING -> execute(
                    new LightningAction(
                            event.getTargetPlayer(),
                            event.getAmount()
                    )
            );

            default -> {
            }

        }

    }

    public EventQueue getQueue() {
        return queue;
    }

}