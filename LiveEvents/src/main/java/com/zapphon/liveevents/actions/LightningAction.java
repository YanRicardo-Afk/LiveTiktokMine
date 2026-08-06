package com.zapphon.liveevents.actions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LightningAction implements Action {

    private final Player player;
    private final int amount;

    public LightningAction(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public void execute() {

        Location baseLocation = player.getLocation();

        for (int i = 0; i < amount; i++) {

            double offsetX = (Math.random() * 10) - 5;
            double offsetZ = (Math.random() * 10) - 5;

            Location location = baseLocation.clone().add(
                offsetX,
                0,
                offsetZ
            );

            player.getWorld().strikeLightning(location);
        }

        player.sendMessage(
            "§eCaíram §6" + amount + " §eraio(s) ao seu redor!"
        );
    }
}