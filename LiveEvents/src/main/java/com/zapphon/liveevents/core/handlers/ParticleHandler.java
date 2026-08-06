package com.zapphon.liveevents.core.handlers;

import com.zapphon.liveevents.core.events.ParticleEvent;

public class ParticleHandler implements EventHandler<ParticleEvent> {

    @Override
    public void handle(ParticleEvent event) {

        event.getTarget().getWorld().spawnParticle(
                event.getParticle(),
                event.getTarget().getLocation().add(0, 1, 0),
                event.getAmount(),
                1,
                1,
                1,
                0.1
        );
    }
}