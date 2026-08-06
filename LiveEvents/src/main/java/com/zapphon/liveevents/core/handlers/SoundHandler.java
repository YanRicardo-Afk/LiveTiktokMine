package com.zapphon.liveevents.core.handlers;

import com.zapphon.liveevents.core.events.SoundEvent;

public class SoundHandler implements EventHandler<SoundEvent> {

    @Override
    public void handle(SoundEvent event) {

        event.getTarget().playSound(
                event.getTarget().getLocation(),
                event.getSound(),
                event.getVolume(),
                event.getPitch()
        );
    }
}