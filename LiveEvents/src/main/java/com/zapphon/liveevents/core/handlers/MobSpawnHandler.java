package com.zapphon.liveevents.core.handlers;

import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.core.events.MobSpawnEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

public class MobSpawnHandler implements EventHandler<MobSpawnEvent> {

    private final NamespacedKey viewerKey;
    private final NamespacedKey mobTypeKey;

    public MobSpawnHandler(LiveEvents plugin) {
        this.viewerKey = new NamespacedKey(plugin, "viewer_name");
        this.mobTypeKey = new NamespacedKey(plugin, "mob_type");
    }

    @Override
    public void handle(MobSpawnEvent event) {

        Location baseLocation = event.getTarget().getLocation();

        for (int i = 0; i < event.getAmount(); i++) {

            double offsetX = (Math.random() * 8) - 4;
            double offsetZ = (Math.random() * 8) - 4;

            Location spawnLocation = baseLocation.clone().add(
                    offsetX,
                    1,
                    offsetZ
            );

            Entity entity = event.getTarget()
                    .getWorld()
                    .spawnEntity(spawnLocation, event.getEntity());

            if (event.getViewer() != null && !event.getViewer().isBlank()) {

                entity.customName(Component.text(event.getViewer()));
                entity.setCustomNameVisible(true);

                entity.getPersistentDataContainer().set(
                        viewerKey,
                        PersistentDataType.STRING,
                        event.getViewer()
                );

                entity.getPersistentDataContainer().set(
                        mobTypeKey,
                        PersistentDataType.STRING,
                        traduzirMob(event)
                );
            }
        }
    }

    private String traduzirMob(MobSpawnEvent event) {

        return switch (event.getEntity()) {
            case CREEPER -> "creeper";
            case ZOMBIE -> "zumbi";
            case SKELETON -> "esqueleto";
            case SPIDER -> "aranha";
            case PIG -> "porco";
            default -> event.getEntity().name().toLowerCase();
        };
    }
}