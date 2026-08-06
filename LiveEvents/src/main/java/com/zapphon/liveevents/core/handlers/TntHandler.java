package com.zapphon.liveevents.core.handlers;

import com.zapphon.liveevents.core.events.TntEvent;
import org.bukkit.Location;
import org.bukkit.entity.TNTPrimed;

public class TntHandler implements EventHandler<TntEvent> {

    @Override
    public void handle(TntEvent event) {

        Location baseLocation = event.getTarget().getLocation();

        for (int i = 0; i < event.getAmount(); i++) {

            double offsetX = (Math.random() * 10) - 5;
            double offsetZ = (Math.random() * 10) - 5;
            double offsetY = 5 + (Math.random() * 4);

            Location spawnLocation = baseLocation.clone().add(
                    offsetX,
                    offsetY,
                    offsetZ
            );

            TNTPrimed tnt = event.getTarget()
                    .getWorld()
                    .spawn(spawnLocation, TNTPrimed.class);

            tnt.setFuseTicks(80);
        }
    }
}