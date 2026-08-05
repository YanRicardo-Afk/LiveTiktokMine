package com.zapphon.liveevents.actions;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnMobAction implements Action {

    private final Player player;
    private final EntityType entityType;
    private final int amount;

    public SpawnMobAction(Player player, EntityType entityType, int amount) {
        this.player = player;
        this.entityType = entityType;
        this.amount = amount;
    }

    @Override
    public void execute() {

        Location baseLocation = player.getLocation();

        for (int i = 0; i < amount; i++) {

            double offsetX = (Math.random() * 8) - 4;
            double offsetZ = (Math.random() * 8) - 4;

            Location spawnLocation = baseLocation.clone().add(
                offsetX,
                1,
                offsetZ
            );

            player.getWorld().spawnEntity(
                spawnLocation,
                entityType
            );
        }

        player.sendMessage(
            "§aForam invocados §e" +
            amount +
            " §a" +
            entityType.name().toLowerCase() +
            "(s)!"
        );
    }
}