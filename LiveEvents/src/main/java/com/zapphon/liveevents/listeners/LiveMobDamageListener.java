package com.zapphon.liveevents.listeners;

import com.zapphon.liveevents.LiveEvents;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;

public class LiveMobDamageListener implements Listener {

    private final NamespacedKey viewerKey;
    private final NamespacedKey mobTypeKey;
    private final NamespacedKey lastViewerKey;
    private final NamespacedKey lastMobTypeKey;
    private final NamespacedKey lastAttackTimeKey;

    public LiveMobDamageListener(LiveEvents plugin) {

        viewerKey = new NamespacedKey(plugin, "viewer_name");
        mobTypeKey = new NamespacedKey(plugin, "mob_type");

        lastViewerKey =
            new NamespacedKey(plugin, "last_live_attacker_viewer");

        lastMobTypeKey =
            new NamespacedKey(plugin, "last_live_attacker_mob");

        lastAttackTimeKey =
            new NamespacedKey(plugin, "last_live_attack_time");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        Entity attacker = descobrirAtacante(event.getDamager());

        if (attacker == null) {
            return;
        }

        String viewerName =
            attacker.getPersistentDataContainer().get(
                viewerKey,
                PersistentDataType.STRING
            );

        String mobType =
            attacker.getPersistentDataContainer().get(
                mobTypeKey,
                PersistentDataType.STRING
            );

        if (viewerName == null || mobType == null) {
            return;
        }

        player.getPersistentDataContainer().set(
            lastViewerKey,
            PersistentDataType.STRING,
            viewerName
        );

        player.getPersistentDataContainer().set(
            lastMobTypeKey,
            PersistentDataType.STRING,
            mobType
        );

        player.getPersistentDataContainer().set(
            lastAttackTimeKey,
            PersistentDataType.LONG,
            System.currentTimeMillis()
        );
    }

    private Entity descobrirAtacante(Entity damager) {

        if (!(damager instanceof Projectile projectile)) {
            return damager;
        }

        ProjectileSource shooter = projectile.getShooter();

        if (shooter instanceof Entity entity) {
            return entity;
        }

        return null;
    }
}