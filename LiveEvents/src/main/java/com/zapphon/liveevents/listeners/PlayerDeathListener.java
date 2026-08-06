package com.zapphon.liveevents.listeners;

import com.zapphon.liveevents.LiveEvents;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerDeathListener implements Listener {

    private final NamespacedKey lastViewerKey;
    private final NamespacedKey lastMobTypeKey;
    private final NamespacedKey lastAttackTimeKey;

    public PlayerDeathListener(LiveEvents plugin) {

        lastViewerKey =
            new NamespacedKey(plugin, "last_live_attacker_viewer");

        lastMobTypeKey =
            new NamespacedKey(plugin, "last_live_attacker_mob");

        lastAttackTimeKey =
            new NamespacedKey(plugin, "last_live_attack_time");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        PersistentDataContainer data =
            player.getPersistentDataContainer();

        String viewerName = data.get(
            lastViewerKey,
            PersistentDataType.STRING
        );

        String mobType = data.get(
            lastMobTypeKey,
            PersistentDataType.STRING
        );

        Long attackTime = data.get(
            lastAttackTimeKey,
            PersistentDataType.LONG
        );

        if (
            viewerName == null ||
            mobType == null ||
            attackTime == null
        ) {
            return;
        }

        long elapsed =
            System.currentTimeMillis() - attackTime;

        if (elapsed <= 10000) {

            event.deathMessage(
                Component.text(
                    player.getName() +
                    " foi morto por um " +
                    mobType +
                    " enviado por " +
                    viewerName +
                    "."
                )
            );
        }

        limparDados(data);
    }

    private void limparDados(PersistentDataContainer data) {

        data.remove(lastViewerKey);
        data.remove(lastMobTypeKey);
        data.remove(lastAttackTimeKey);
    }
}