package com.zapphon.liveevents.core.scheduler;

import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.core.engine.LiveEngine;
import org.bukkit.scheduler.BukkitRunnable;

public class EventScheduler extends BukkitRunnable {

    private final LiveEngine engine;

    public EventScheduler(
            LiveEngine engine
    ) {

        this.engine = engine;

    }

    @Override
    public void run() {

        engine.tick();

    }

    public void start(
            LiveEvents plugin
    ) {

        runTaskTimer(
                plugin,
                1L,
                2L
        );

    }

}