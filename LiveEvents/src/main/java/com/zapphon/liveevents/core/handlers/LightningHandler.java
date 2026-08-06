package com.zapphon.liveevents.core.handlers;

import com.zapphon.liveevents.core.events.LightningEvent;
import org.bukkit.Location;

public class LightningHandler implements EventHandler<LightningEvent> {

    @Override
    public void handle(LightningEvent event) {

        Location baseLocation = event.getTarget().getLocation();

        for (int i = 0; i < event.getAmount(); i++) {

            double offsetX = (Math.random() * 10) - 5;
            double offsetZ = (Math.random() * 10) - 5;

            Location location = baseLocation.clone().add(
                    offsetX,
                    0,
                    offsetZ
            );

            event.getTarget()
                    .getWorld()
                    .strikeLightning(location);
        }
    }
}