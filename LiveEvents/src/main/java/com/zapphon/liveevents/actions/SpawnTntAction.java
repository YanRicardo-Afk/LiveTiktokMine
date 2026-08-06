package com.zapphon.liveevents.actions;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class SpawnTntAction implements Action {

    private final Player player;
    private final int amount;

    public SpawnTntAction(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public void execute() {

        Location baseLocation = player.getLocation();

        for (int i = 0; i < amount; i++) {

            double offsetX = (Math.random() * 10) - 5;
            double offsetZ = (Math.random() * 10) - 5;
            double offsetY = 5 + (Math.random() * 4);

            Location spawnLocation = baseLocation.clone().add(
                offsetX,
                offsetY,
                offsetZ
            );

            TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(
                spawnLocation,
                EntityType.TNT
            );

            tnt.setFuseTicks(80);
        }

        player.sendMessage(
            "§cChuva de TNT iniciada: §e" + amount + " TNT(s)!"
        );
    }
}