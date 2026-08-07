package com.zapphon.liveevents.core.handlers;

import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.core.events.MobSpawnEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class MobSpawnHandler
        implements EventHandler<MobSpawnEvent> {

    private final NamespacedKey viewerKey;
    private final NamespacedKey mobTypeKey;

    public MobSpawnHandler(LiveEvents plugin) {
        viewerKey = new NamespacedKey(plugin, "viewer_name");
        mobTypeKey = new NamespacedKey(plugin, "mob_type");
    }

    @Override
    public void handle(MobSpawnEvent event) {

        Location baseLocation =
                event.getTarget().getLocation();

        for (int i = 0; i < event.getAmount(); i++) {

            double offsetX = (Math.random() * 8) - 4;
            double offsetZ = (Math.random() * 8) - 4;

            Location spawnLocation = baseLocation
                    .clone()
                    .add(offsetX, 1, offsetZ);

            Entity entity = event.getTarget()
                    .getWorld()
                    .spawnEntity(
                            spawnLocation,
                            event.getEntity()
                    );

            adicionarNome(event, entity);
            adicionarArmadura(event, entity);
        }
    }

    private void adicionarNome(
            MobSpawnEvent event,
            Entity entity
    ) {

        String viewer = event.getViewer();

        if (viewer == null || viewer.isBlank()) {
            return;
        }

        entity.customName(Component.text(viewer));
        entity.setCustomNameVisible(true);

        entity.getPersistentDataContainer().set(
                viewerKey,
                PersistentDataType.STRING,
                viewer
        );

        entity.getPersistentDataContainer().set(
                mobTypeKey,
                PersistentDataType.STRING,
                traduzirMob(event)
        );
    }

    private void adicionarArmadura(
            MobSpawnEvent event,
            Entity entity
    ) {

        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        String armor = event.getArmor();

        if (armor == null || armor.isBlank()) {
            return;
        }

        String prefix = armor.toUpperCase();

        EntityEquipment equipment =
                livingEntity.getEquipment();

        if (equipment == null) {
            return;
        }

        Material helmet =
                Material.matchMaterial(prefix + "_HELMET");

        Material chestplate =
                Material.matchMaterial(prefix + "_CHESTPLATE");

        Material leggings =
                Material.matchMaterial(prefix + "_LEGGINGS");

        Material boots =
                Material.matchMaterial(prefix + "_BOOTS");

        if (helmet != null) {
            equipment.setHelmet(new ItemStack(helmet));
        }

        if (chestplate != null) {
            equipment.setChestplate(
                    new ItemStack(chestplate)
            );
        }

        if (leggings != null) {
            equipment.setLeggings(
                    new ItemStack(leggings)
            );
        }

        if (boots != null) {
            equipment.setBoots(new ItemStack(boots));
        }
    }

    private String traduzirMob(MobSpawnEvent event) {

        return switch (event.getEntity()) {
            case CREEPER -> "creeper";
            case ZOMBIE -> "zumbi";
            case SKELETON -> "esqueleto";
            case SPIDER -> "aranha";
            case PIG -> "porco";
            case GHAST -> "ghast";
            default -> event.getEntity()
                    .name()
                    .toLowerCase();
        };
    }
}