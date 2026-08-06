package com.zapphon.liveevents.actions;

import com.zapphon.liveevents.LiveEvents;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class SpawnMobAction implements Action {

    private final LiveEvents plugin;
    private final Player player;
    private final EntityType entityType;
    private final int amount;
    private final String viewerName;

    public SpawnMobAction(
        LiveEvents plugin,
        Player player,
        EntityType entityType,
        int amount,
        String viewerName
    ) {
        this.plugin = plugin;
        this.player = player;
        this.entityType = entityType;
        this.amount = amount;
        this.viewerName = viewerName;
    }

    @Override
    public void execute() {

        Location baseLocation = player.getLocation();

        NamespacedKey viewerKey =
            new NamespacedKey(plugin, "viewer_name");

        NamespacedKey mobTypeKey =
            new NamespacedKey(plugin, "mob_type");

        for (int i = 0; i < amount; i++) {

            double offsetX = (Math.random() * 8) - 4;
            double offsetZ = (Math.random() * 8) - 4;

            Location spawnLocation = baseLocation.clone().add(
                offsetX,
                1,
                offsetZ
            );

            Entity entity = player.getWorld().spawnEntity(
                spawnLocation,
                entityType
            );

            if (viewerName != null && !viewerName.isBlank()) {

                entity.customName(
                    net.kyori.adventure.text.Component.text(viewerName)
                );

                entity.setCustomNameVisible(true);

                entity.getPersistentDataContainer().set(
                    viewerKey,
                    PersistentDataType.STRING,
                    viewerName
                );

                entity.getPersistentDataContainer().set(
                    mobTypeKey,
                    PersistentDataType.STRING,
                    traduzirMob(entityType)
                );
            }
        }

        if (viewerName != null && !viewerName.isBlank()) {
            player.sendMessage(
                "§a" + amount + " " +
                traduzirMob(entityType) +
                "(s) enviado(s) por §e" +
                viewerName + "§a!"
            );
        } else {
            player.sendMessage(
                "§aForam invocados §e" +
                amount + " §a" +
                traduzirMob(entityType) +
                "(s)!"
            );
        }
    }

    private String traduzirMob(EntityType type) {

        return switch (type) {
            case CREEPER -> "creeper";
            case ZOMBIE -> "zumbi";
            case SKELETON -> "esqueleto";
            case SPIDER -> "aranha";
            case PIG -> "porco";
            default -> type.name().toLowerCase();
        };
    }
}