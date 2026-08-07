package com.zapphon.liveevents.core.handlers;

import com.zapphon.liveevents.core.events.WolfFollowEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Wolf;

public class WolfFollowHandler
        implements EventHandler<WolfFollowEvent> {

    @Override
    public void handle(WolfFollowEvent event) {

        Location location = event.getTarget()
                .getLocation()
                .clone()
                .add(2, 0, 2);

        Wolf wolf = event.getTarget()
                .getWorld()
                .spawn(location, Wolf.class);

        wolf.setTamed(true);
        wolf.setOwner(event.getTarget());
        wolf.setSitting(false);

        String viewer = event.getViewer() == null
                ? "Novo seguidor"
                : event.getViewer();

        wolf.customName(Component.text(viewer));
        wolf.setCustomNameVisible(true);
    }
}